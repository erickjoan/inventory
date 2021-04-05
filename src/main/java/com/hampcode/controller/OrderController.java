package com.hampcode.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hampcode.common.pagination.PageRender;
import com.hampcode.model.Customer;
import com.hampcode.model.Order;
import com.hampcode.model.OrderItem;
import com.hampcode.model.Product;
import com.hampcode.service.CustomerService;
import com.hampcode.service.OrderService;
import com.hampcode.service.ProductService;


@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/list")
	public String getAllCustomers(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		try {
			Pageable pageRequest = PageRequest.of(page, 2);

			Page<Customer> customers = customerService.getAll(pageRequest);

			PageRender<Customer> pageRender = new PageRender<Customer>("/orders/list/", customers);

			model.addAttribute("title", "Clientes");

			model.addAttribute("customers", customers);
			model.addAttribute("page", pageRender);

		} catch (Exception e) {

		}

		return "order/order";
	}

	@GetMapping("/form/{customerId}")
	public String newOrder(@PathVariable(value = "customerId") Long customerId, Model model) {
		try {
			Optional<Customer> customer = customerService.getOne(customerId);
			if (!customer.isPresent()) {
				model.addAttribute("info", "Cliente no existe");
				return "redirect:/orders/list";
			} else {
				Order order = new Order();
				order.setCustomer(customer.get());
				model.addAttribute("order", order);
				model.addAttribute("title", "Factura");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "order/form";
	}

	@PostMapping("/save")
	public String saveOrder(Order order, Model model,
			@RequestParam(name = "item_id[]", required = true) Long[] itemId,
			@RequestParam(name = "quantity[]", required = true) Integer[] quantity, SessionStatus status) {
		try {

			if (itemId == null || itemId.length == 0) {
				model.addAttribute("info", "La orden no tiene productos");
				return "orden/form";
			}

			for (int i = 0; i < itemId.length; i++) {
				Optional<Product> product = productService.getOne(itemId[i]);
				if (product.isPresent()) {
					OrderItem orderItem = new OrderItem();
					orderItem.setQuantity(quantity[i]);
					orderItem.setProduct(product.get());
					order.addItemOrder(orderItem);
				}
			}

			orderService.saveOrUpdate(order);
			status.setComplete();
			model.addAttribute("success", "Orden Generada");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "redirect:/orders/detail/" + order.getCustomer().getId();
	}

	@GetMapping("/detail/{id}")
	public String detailOrder(@PathVariable(value = "id") Long orderId, Model model) {

		try {
			Optional<Order> order = orderService.getOrderByIdWithCustomerWithOrderItemWithProduct(orderId);

			if (!order.isPresent()) {
				model.addAttribute("error", "Orden no existe");
				return "redirect:/orders/list";
			}

			model.addAttribute("order", order.get());
			model.addAttribute("title", "Orden");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "order/detailorder";
	}

	@GetMapping(value = "/customers/{id}")
	public String orderByCustomer(@PathVariable(value = "id") Long customerId, Model model,
			RedirectAttributes flash) {

		Optional<Customer> customer;
		List<Order> orders=new ArrayList<>();
		try {
			customer = customerService.getOne(customerId);
			
			if (!customer.isPresent()) {
				flash.addFlashAttribute("error", "El cliente no existe");
				return "redirect:/invoices/list";
			}else {
				orders=orderService.getOrderByCustomerId(customerId);
				model.addAttribute("customer",customer.get());
				model.addAttribute("orders",orders);
				model.addAttribute("title", "Cliente");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "order/ordercustomer";
	}
}
