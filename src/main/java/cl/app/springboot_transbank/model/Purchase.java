package cl.app.springboot_transbank.model;

/**
 * Clase Purchase (Compra).
 */
public class Purchase {

	/** El valor. */
	private double amount;
	
	/** El ID de session. */
	private String sessionId;
	
	/** La orden de compra. */
	private String buyOrder;
	
	/**
	 * Gets el valor.
	 *
	 * @return el valor
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Sets el valor.
	 *
	 * @param argumento de valor
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Gets el ID de session.
	 *
	 * @return el ID de session
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * Sets el ID de session.
	 *
	 * @param argumento de ID de session
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * Gets la orden de compra.
	 *
	 * @return la orden de compra
	 */
	public String getBuyOrder() {
		return buyOrder;
	}
	
	/**
	 * Sets la orden de compra.
	 *
	 * @param argumento de orden de compra
	 */
	public void setBuyOrder(String buyOrder) {
		this.buyOrder = buyOrder;
	}
	
	
}
