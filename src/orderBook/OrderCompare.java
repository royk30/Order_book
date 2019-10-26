package orderBook;

import java.util.Comparator;

public class OrderCompare implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		return (int)(o1.getPrice() - o2.getPrice());
	}

}
