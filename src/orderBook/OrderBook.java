package orderBook;

public class OrderBook {
	private OrdersMap bids;
	private OrdersMap asks;
	
	public void add_order(Order order) {
		if(order.getIsBid()) {
			bids.insert(order);
		}
		else {
			asks.insert(order);
		}
	}
	
	public void modify_order(Order order) {
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
	
	private void processMarketOrder(Order order) {
		int quantity = order.getQuantity();
		
		if(order.getIsBid()) {
			
		}
	}
}
