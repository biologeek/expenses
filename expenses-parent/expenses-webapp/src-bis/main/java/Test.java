import io.biologeek.expenses.domain.beans.security.OwnPasswordEncoder;

public class Test {
public static void main(String[] args) {
	
	
	System.out.println(new OwnPasswordEncoder().encode("a"));
	
}
}
