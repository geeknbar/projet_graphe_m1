package context;

/**
 * @author Mickael
 *
 */
public class Sommet
{

	private Object value;
	
	public Sommet(Object value)
	{
		this.value= value;
	}

	public Object getValue() 
	{
		return value;
	}

	public void setValue(Object value) 
	{
		this.value = value;
	}

	@Override
	public String toString() 
	{
		return "Sommet [value=" + value + "]";
	}
}
