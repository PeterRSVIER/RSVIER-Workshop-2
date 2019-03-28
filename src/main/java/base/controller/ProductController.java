package base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import base.Product;
import base.repository.ProductRepository;


@RequestMapping("/product")
@Controller
public class ProductController {
	
	ProductRepository productRepository;
	
	@Autowired
	ProductController (ProductRepository productRepository){
		this.productRepository = productRepository;
	}
	
	@ModelAttribute (name = "productList")
	public List<Product> productList() {
		List<Product> productList = new ArrayList<>();
		productList = (List<Product>) productRepository.findAll();
		return productList;
	}
	
	@ModelAttribute(name = "product")
	public Product product() {
		return new Product();
	}

	@GetMapping
	public String showProducts() {
		return "product/product";
	}
	
	@GetMapping(value = "create")
	public String createProductForm(Model model) {
		return "product/createProduct";
	}
	
	@PostMapping(value = "create")
	public String createProduct(Product product, Model model) {
		productRepository.save(product);
		return ("redirect:/medewerkers");
	}
	
	@GetMapping(value = "update/{id}")
	public String updateProduct(@PathVariable("id") int id, Model model) {
		//Try block nodig?
		Product product = productRepository.findById(id).get();
		model.addAttribute("productToUpdate", product);
		System.out.println("Product in GetMapping delete/{id} =" + product);
		return "product/updateProduct";
	}
	
	@PostMapping(value ="update")
	public String confirmUpdateProduct(Product product, Model model) {
		System.out.println("updating product:" + product);
		productRepository.save(product);
		return ("redirect:/medewerkers");
	}
	

	@GetMapping(value = "delete/{id}")
	public String deleteProduct(@PathVariable("id") int id, Model model) {
		//Try block nodig?
		Product product = productRepository.findById(id).get();
		model.addAttribute("productToDelete", product);
		System.out.println("Product in GetMapping delete/{id} =" + product);
		return "product/deleteProduct";
	}
	
	@PostMapping(value ="delete")
	public String confirmDeleteProduct(Product product, Model model) {
		System.out.println("deleting product:" + product);
		productRepository.delete(product);
		return ("redirect:/medewerkers");
	}
}
