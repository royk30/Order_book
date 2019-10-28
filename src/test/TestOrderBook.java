package test;

import org.junit.Test;

import orderBook.Order;
import orderBook.OrderBook;
import orderBook.OrderType;

import static org.junit.Assert.assertEquals;

public class TestOrderBook {
	
	OrderBook ob;
	
	private void setup() {
		ob = new OrderBook();
		int id = 1;
		for(int i=0; i<5; i++) {
			ob.add_order(new Order(Long.valueOf(id), System.currentTimeMillis(), true, 100 + i * 10, id * 10, "sami", OrderType.LIMIT));
			id++;
		}
		
		for(int i=0; i<5; i++) {
			ob.add_order(new Order(Long.valueOf(id), System.currentTimeMillis(), false, 100 + i * 10, id * 10, "moshe", OrderType.LIMIT));
			id++;
		}
	}

	@Test
   public void testAdd() {
		setup();
	    assertEquals(100,ob.getBids().getOrderById(1L).getPrice(), 0.0);
	}
	
	@Test
	public void testModify() {
		setup();
		Order order = ob.getBids().getOrderById(4L);
		assertEquals(40,order.getQuantity());
		Order changeOrder = new Order(order.getId(), order.getTimestamp(), order.getIsBid(), order.getPrice(), order.getQuantity()-15, order.getVenue(), order.getType());
		try {
			ob.modify_order(changeOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(25,order.getQuantity());
	}
	
	@Test
	public void testDelete() {
		setup();
		Order order = ob.getBids().getOrderById(3L);
		assertEquals(true,order != null);
		ob.delete_order(order);
		order = ob.getBids().getOrderById(3L);
		assertEquals(true,order == null);
	}
	
	@Test
	public void testBestBid() {
		setup();
		assertEquals(140,ob.get_best_bid(), 0.0);
		
		Order order = ob.getBids().getOrderById(5L);
		ob.delete_order(order);
		assertEquals(130,ob.get_best_bid(), 0.0);
	}
	
	@Test
	public void testBestOffer() {
		setup();
		assertEquals(100,ob.get_best_offer(), 0.0);
		
		Order order = ob.getAsks().getOrderById(6L);
		ob.delete_order(order);
		assertEquals(110,ob.get_best_offer(), 0.0);
	}
	
	@Test
	public void testProcessMarketOrder() {
		setup();
		Order order =  ob.getAsks().getOrderById(6L);
		assertEquals(60,order.getQuantity());
		Order marketOrder = new Order(20L, System.currentTimeMillis(), true, 0, 10, "sami", OrderType.MARKET);
		
		ob.process_order(marketOrder);
		assertEquals(50,order.getQuantity());
	}
}
