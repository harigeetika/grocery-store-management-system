package com.cts.shoppingcartservice.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {
	
	private long productId;
	private String productName;
	private long productQuantity;
	private long productSubTotalPrice;
	private byte[] productImage;
}
