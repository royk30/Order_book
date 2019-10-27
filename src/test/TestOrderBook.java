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
		
		Order o1 = new Order(1L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o2 = new Order(2L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o3 = new Order(3L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o4 = new Order(4L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o5 = new Order(5L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o6 = new Order(6L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o7 = new Order(7L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		Order o8 = new Order(8L, 111L, true, 100, 50, "sami", OrderType.LIMIT);
		
		ob.add_order(o1);
		ob.add_order(o2);
		ob.add_order(o3);
		ob.add_order(o4);
		ob.add_order(o5);
		ob.add_order(o6);
		ob.add_order(o7);
		ob.add_order(o8);
	}
	@Test
   public void testAdd() {
		setup();
	      String str = "Junit is working fine";
	      assertEquals("Junit is working fine",str);
	   }
}
