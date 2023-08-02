package com.cts.shoppingcartservice.service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cts.shoppingcartservice.common.AddToCartDto;
import com.cts.shoppingcartservice.common.AddToCartResponse;
import com.cts.shoppingcartservice.common.CartItems;
import com.cts.shoppingcartservice.common.Product;
import com.cts.shoppingcartservice.entity.Cart;
import com.cts.shoppingcartservice.repository.CartRepository;

import lombok.extern.slf4j.Slf4j;





@Service
@Slf4j
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	

	
	public AddToCartResponse cartResponse(long customerId,List<Cart> ct) {
		AddToCartResponse ar = new AddToCartResponse();
		 List<CartItems> ci = new ArrayList<CartItems>();
			long tp=0;
			long cartItemCount=0;
			
			for(Cart c: ct) {
				System.out.println(c);
				CartItems cartItem =  new CartItems();
				cartItem.setProductId(c.getProductId());
				cartItem.setProductName(c.getProductName());
				long subtotal = c.getSubTotal()*c.getProductQuantity();
				cartItem.setProductSubTotalPrice(c.getSubTotal());
				cartItem.setProductImage(c.getProductImage());
				tp=tp+subtotal;
				cartItemCount=cartItemCount+c.getProductQuantity();
				cartItem.setProductQuantity(c.getProductQuantity());
				ci.add(cartItem);
			}
			ar.setCartItems(ci);
			ar.setCustomerId(customerId);
			ar.setTotalBill(tp);
			ar.setCartItemsQuantity(cartItemCount);
			return ar;

	}
	
	public AddToCartResponse getCartItemsById(long customerId){
		
        List<Cart> ct = cartRepository.getCartItemsById(customerId);
        System.out.println(ct);
        AddToCartResponse ar = cartResponse(customerId,ct);
        return ar;
       
	}
	
	public List<Cart> getCartItems(long customerId){
		
        List<Cart> ct = cartRepository.getCartItemsById(customerId);
        return ct;
       
	}
	
	public long countCartItems(long custId)
	{
		long count = cartRepository.countByCustId(custId);
		return count;
	}
	
	public void delCartByCustomerId(long custId) {
		log.info("customer Id "+custId+"cart deleted as order has been placed");	
		cartRepository.delCartAfterOrder(custId);
	}
	
	public AddToCartResponse deleteCartItemById(long customerId,long productId) {
		
		cartRepository.delCartItemById(customerId,productId);
		List<Cart> ct = cartRepository.getCartItemsById(customerId);
        AddToCartResponse ar = cartResponse(customerId,ct);
        log.info("Cart item with Id"+productId+" is deleted");
        return ar;
	}
	
	public Cart checkProductInCart(long cid,long pid)
	{
		Cart c = cartRepository.getProductFromCart(cid,pid);
		if(c==null)
		{
			return null;
		}
		else {
			return c;
		}
	}
	
	
	public AddToCartResponse addToCart(long cid,AddToCartDto addToCartDto) {
			
		Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+addToCartDto.getProductId(),Product.class);
		
		Cart cart = new Cart();
		cart.setCustomerId(cid);
		cart.setProductId(addToCartDto.getProductId());
		long q= addToCartDto.getProductQuantity();
		long price= product.getProductPrice();
		cart.setSubTotal(price);
		cart.setProductName(product.getProductName());
		cart.setProductQuantity(q);
		cart.setProductImage(product.getProductImage());
		cartRepository.save(cart);
			
		log.info("Item with Id "+addToCartDto.getProductId()+" succesfully addded to cart");
			
		List<Cart> ct = cartRepository.getCartItemsById(cid);
        AddToCartResponse ar = cartResponse(cid,ct);
        return ar;
		
	}
	
	public String updateAndAddProduct(long cid,AddToCartDto addToCartDto)
	{
		long pid = addToCartDto.getProductId();
		Cart c1 = checkProductInCart(cid,pid);
		long q1=c1.getProductQuantity();
		long qyt = q1+addToCartDto.getProductQuantity();
		cartRepository.setNewQuantity(cid,pid,qyt);
		return "Updated";
	}
	
	public String addToCartt(long cid,AddToCartDto addToCartDto) {
			
		long pid = addToCartDto.getProductId();
		Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+addToCartDto.getProductId(),Product.class);
		
		Cart cart = new Cart();
		cart.setCustomerId(cid);
		cart.setProductId(addToCartDto.getProductId());
		long q= addToCartDto.getProductQuantity();
		long price= product.getProductPrice();
		cart.setSubTotal(price*q);
		cart.setProductName(product.getProductName());
		cart.setProductQuantity(q);
		cart.setProductImage(product.getProductImage());
		cartRepository.save(cart);
			
		log.info("Item with Id "+addToCartDto.getProductId()+" succesfully addded to cart");
		return "Item with Id "+addToCartDto.getProductId()+" succesfully addded to cart";
			
	}

		
}
	

