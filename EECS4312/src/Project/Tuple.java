package Project;

	public class Tuple<X, Y> { 
	    public final X x; 
	    public final Y y; 
	    public enum tryhard {gn, sp};
	    
	    public Tuple(X x, Y y) { 
	        this.x = x; 
	        this.y = y; 
	    }

	    @Override
	    public String toString() {
	        return "(" + x + "," + y + ")";
	    }

	    @Override
	    public boolean equals(Object other) {
	        if (other == null) {
	            return false;
	        }
	        if (other == this) {
	            return true;
	        }
	        if (!(other instanceof Tuple)){
	            return false;
	        }
	        Tuple<X,Y> other_ = (Tuple<X,Y>) other;
	        return other_.x == this.x && other_.y == this.y;
	    }
	   
	    public static void main(String[] args)
	    {
	    	System.out.print(testString());
	    }
	    
	    public static boolean testString()
	    {
	    	boolean returnvalue = false;
	    	return returnvalue = true;
	    }
	}
	
