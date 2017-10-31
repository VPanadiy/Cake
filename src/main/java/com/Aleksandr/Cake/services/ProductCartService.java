package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.Product;
import com.Aleksandr.Cake.model.ShoppingCartData;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public interface ProductCartService {

	public void addProductToShoppingCart(ShoppingCartData data);

	public void deleteProductFromShoppingCart(ShoppingCartData data);

	public void deleteAllProductFromShoppingCartByProductId(ShoppingCartData data);

	public void addCondimentToProductInShoppingCart(ShoppingCartData data);

	public void deleteCondimentFromProductInShoppingCart(ShoppingCartData data);

	public TreeMap<Integer, BigDecimal>  calculateProductsSumByIdInShoppingCart(Map<Integer, TreeMap<Integer, Product>> shoppingCart);

	public BigDecimal calculateProductsSumAllInShoppingCart(TreeMap<Integer, BigDecimal> sumByProductId);
	
	public BigDecimal calculateDiscount(Map<Integer, TreeMap<Integer, Product>> shoppingCart);
}
