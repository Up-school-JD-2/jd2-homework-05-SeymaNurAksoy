package packageCreditCart;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			atm();
			if(pay()) {
				atm();
				throw new SystemNotWorkingException("Sistemsel hata tekrar deneyiniz.");
			}
			
		} catch (SystemNotWorkingException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void atm() throws SystemNotWorkingException {
		Scanner scanner = new Scanner(System.in);
		boolean bug=false;
		try {

			System.out.print("Ödeme Tutarı: ");
			String payment = scanner.nextLine();
			if (!totalPaymentCheck(payment)) {
				throw new PaymentException("Ödeme tutarı virgül içeremez ve sıfırdan büyül olmalıdır.", payment);

			}

			System.out.print("Kart Numarası: ");
			String cartNumber = scanner.nextLine();
			if (!cartCheck(cartNumber)) {
				throw new CartNumberException("Kart Numarası 16 haneli ve rakamlardan oluşmalıdır", cartNumber);
			}

			System.out.print("Son Kullanma Tarihi: Yıl/Ay  ");
			String dateYear = scanner.nextLine();
			String dateMonth = scanner.nextLine();

			if (!isValidCartYear(dateYear, dateMonth)) {
				throw new CartDateInvalidException("Kartın Son kullanma tarihi geçersiz.");
			}

			System.out.print("Doğrulama Kodu ");
			String code = scanner.nextLine();
			if (!isValidCode(code)) {
				throw new CodeInvalidException("Doğrulama kodu 3 haneli olmalı ve harf içeremez.");
			}

			System.out.println("Ödeme gerçekleşmiştir.");

		} catch (PaymentException e) {
			System.out.printf("%s hatalı alan: %s", e.getMessage(), e.getText());
			System.out.println();
			bug=true;
			throw new SystemNotWorkingException("");
		} catch (CodeInvalidException | CartNumberException | CartDateInvalidException e) {
			bug=true;
			System.out.println(e.getMessage());
			throw new SystemNotWorkingException("");
		}finally {
			if(pay()) {
				atm();
			}
			if(bug) {
				atm();
			}
		}{
			
		}
	}

	private static boolean pay() {
		Random random = new Random();
		int randomNumber = random.nextInt(100);
		if (randomNumber > 75) {
			return true;
		}
		return false;
	}

	private static boolean totalPaymentCheck(String payment) {
		if (payment.contains(",")) {
			return false;
		}
		int paymentInt = Integer.parseInt(payment);
		if (paymentInt < 0) {
			return false;
		}
		return true;
	}

	private static boolean cartCheck(String cartNumber) {
		if (cartNumber.length() != 1) {
			return false;
		}

		for (char c : cartNumber.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isValidCartYear(String year, String month) {
		DateFormat df = DateFormat.getDateInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		NumberFormat nf = df.getNumberFormat();
		String st = sdf.format(new Date());
		String st2 = sdf2.format(new Date());
		int m = Integer.parseInt(st2);
		int i = Integer.parseInt(st);
		String monthNow = nf.format(m);
		String yearNow = nf.format(i);
		int monthNowInt = Integer.parseInt(monthNow);
		int monthCart = Integer.parseInt(month);
		int yearNow2 = Integer.parseInt(yearNow);
		int cartYear = Integer.parseInt(year);
		if (yearNow2 > cartYear) {
			return false;
		}
		if (monthCart > 12) {
			return false;
		}
		return true;
	}

	private static boolean isValidCode(String code) {

		for (char c : code.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		if (code.length() != 3) {
			return false;
		}
		return true;
	}
}
