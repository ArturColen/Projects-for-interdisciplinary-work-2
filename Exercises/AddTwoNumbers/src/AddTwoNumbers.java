import java.util.*;

class AddTwoNumbers {
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		int number1, number2, sum;
		
		System.out.println("Digite o primeiro número:");
		number1 = sc.nextInt();
		
		System.out.println("Digite o segundo número:");
		number2 = sc.nextInt();
		
		sum = number1 + number2;
		
		System.out.println("Resultado: " + sum);
	}
}
