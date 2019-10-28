package orderBook;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;

public class OrdersMap {
	TreeMap<Double, Set<Order>> priceTree = new TreeMap<Double, Set<Order>>();
	HashMap<Long, Order> orderMap = new HashMap<Long, Order>();
	
	public void insert(Order order) {
		Long orderId = order.getId();
		
		if(orderMap.containsKey(orderId)) {
			System.out.println("Order exist");
			return;
		}
		
		double price = order.getPrice();
		
		if(!priceTree.containsKey(price)) {
			priceTree.put(price, new LinkedHashSet<Order>());
		}
		
		priceTree.get(price).add(order);
		orderMap.put(orderId, order);
	}
	
	public void remove(Order order) {
		Long orderId = order.getId();
		
		if(!orderMap.containsKey(orderId)) {
			System.out.println("Order not exist");
			return;
		}
		
		orderMap.remove(orderId);
		
		double price = order.getPrice();
		priceTree.get(price).remove(order);
		if(priceTree.get(price).size() == 0) {
			priceTree.remove(price);
		}
	}
	
	public void update(Order updatedOrder) throws Exception {
		Long orderId = updatedOrder.getId();
		
		if(!orderMap.containsKey(orderId)) {
			System.out.println("Order not exist");
			return;
		}
		
		if(noChangeOnNoQuantityFields(updatedOrder)) {
			updateOrderQuantity(orderId, updatedOrder.getQuantity());
		} else {
			throw new Exception("Allow to update only quantity");
		}
	}
	
	public void updateOrderQuantity(Long id, int quantity) {
		orderMap.get(id).setQuantity(quantity);
	}
	
	public Double getMax() {
		if(priceTree.size() > 0) {
			return priceTree.lastKey();
		}
		return null;
	}
	
	public Double getMin() {
		if(priceTree.size() > 0) {
			return priceTree.firstKey();
		}
		return null;
	}
	
	public Set<Order> getMaxSet() {
		return priceTree.get(getMax());
	}
	
	public Set<Order> getMinSet() {
		return priceTree.get(getMin());
	}
	
	private Boolean noChangeOnNoQuantityFields(Order updatedOrder) {
		Long orderId = updatedOrder.getId();
		
		Order currentOrder = orderMap.get(orderId);
		
		return currentOrder.getIsBid() == updatedOrder.getIsBid() && currentOrder.getPrice() == updatedOrder.getPrice() && 
				currentOrder.getTimestamp() == updatedOrder.getTimestamp() && currentOrder.getType() == updatedOrder.getType() && 
				currentOrder.getVenue() == updatedOrder.getVenue();
	}
	
	public int getNumberOfOrders() {
		return orderMap.size();
	}
	
	public Order getOrderById(Long orderId) {
		if(orderMap.containsKey(orderId)) {
			return orderMap.get(orderId);
		}
		
		return null;
	}
}
