package orderBook;

import java.util.Set;

public class OrderBook {
	private OrdersMap bids = new OrdersMap();
	private OrdersMap asks  = new OrdersMap();
	
	public void add_order(Order order) {
		if(order.getIsBid()) {
			bids.insert(order);
		}
		else {
			asks.insert(order);
		}
	}
	
	public void modify_order(Order order) throws Exception {
		if(order.getIsBid()) {
			bids.update(order);
		}
		else {
			asks.update(order);
		}		
	}
	
	public void delete_order(Order order) {
		if(order.getIsBid()) {
			bids.remove(order);
		}
		else {
			asks.remove(order);
		}				
	}
	
	public double get_best_bid() {
		return bids.getMax();
	}
	
	public double get_best_offer() {
		return asks.getMin();
	}
	
	public void process_order(Order order) {
		if(order.getType().equals(OrderType.LIMIT)) {
			processLimitOrder(order);
		}
		else {
			processMarketOrder(order);
		}
	}
	
	private void processMarketOrder(Order order) {
		int quantity = order.getQuantity();
		
		if(order.getIsBid()) {
			while(quantity > 0 && asks.getNumberOfOrders() > 0) {
				quantity = processOrder(asks.getMinSet(), quantity, order);
			}
		}
		else {
			while(quantity > 0 && bids.getNumberOfOrders() > 0) {
				quantity = processOrder(bids.getMaxSet(), quantity, order);
			}			
		}
	}
	
	private void processLimitOrder(Order order) {
		int quantity = order.getQuantity();
		
		if(order.getIsBid()) {
			while(asks.getNumberOfOrders() > 0 && quantity > 0 && order.getPrice() >= asks.getMin()) {
				quantity = processOrder(asks.getMinSet(), quantity, order);
			}
			
			if(quantity > 0) {
				order.setQuantity(quantity);
				bids.insert(order);
			}
		}
		else {
			while(bids.getNumberOfOrders() > 0 && quantity > 0 && order.getPrice() >= bids.getMax()) {
				quantity = processOrder(bids.getMaxSet(), quantity, order);
			}
			
			if(quantity > 0) {
				order.setQuantity(quantity);
				asks.insert(order);
			}			
		}
	}
	
	private int processOrder (Set<Order> orders, int remainQuantity, Order currentOrder) {
		while((orders.size() > 0) && (remainQuantity > 0)) {
			Order order = orders.iterator().next();
			if(remainQuantity < order.getQuantity()) {
				if(currentOrder.getIsBid()) {
					asks.updateOrderQuantity(order.getId(), order.getQuantity() - remainQuantity);
				}
				else {
					bids.updateOrderQuantity(order.getId(), order.getQuantity() - remainQuantity);
				}
				remainQuantity = 0;
			}
			else {
				remainQuantity -= order.getQuantity();
				
				if(currentOrder.getIsBid()) {
					asks.remove(order);
				}
				else {
					bids.remove(order);
				}
			}
		}
		return remainQuantity;
		
	}

	public OrdersMap getBids() {
		return bids;
	}

	public OrdersMap getAsks() {
		return asks;
	}
}
