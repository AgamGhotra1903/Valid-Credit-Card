import java.util.Scanner;

public class CreditCard {

    private int sum,accNo,cd;
    private String ccn,mii,iin;
    private boolean vcc;

    public static void main(String[] args) {
        CreditCard cc = new CreditCard();
        cc.input();
        cc.display();
    }

    CreditCard(){
        sum = 0;
        accNo = 0;
        cd = 0;
        mii = null;
        iin = null;
        ccn = null;
        vcc = false;
    }

    public void input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Credit Card Number : ");
        ccn = sc.nextLine();
        ccn = removeNonNumerals(ccn);
        vcc = checkValidCreditCard(ccn);
        cd = Integer.parseInt(String.valueOf(ccn.charAt(ccn.length() - 1)));
        mii = majorIndustryIdentifier(Integer.parseInt(String.valueOf(ccn.charAt(0))));
        iin = industryIdentificationNumber(Integer.parseInt(ccn.substring(0, 6)));
        accNo = Integer.parseInt(ccn.substring(6, ccn.length() - 1));
    }

    public String removeNonNumerals(String ccn){
        String ccn1 = "";
        for(int i = 0 ; i < ccn.length(); i++) {
            char c = ccn.charAt(i);
            if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')
                ccn1 += c;
        }
        return ccn1;
    }

    public boolean checkValidCreditCard(String ccn){
        if(!(ccn.length() >= 12 && ccn.length() <= 19))
            return false;
        return checkSum(Long.parseLong(ccn));
    }

    public boolean checkSum(long ccn){
        String ccn1 = String.valueOf(ccn);
        int count = 0,d,n;
        for(int i = ccn1.length() - 1; i >= 0; i--){
            n = Integer.parseInt(String.valueOf(ccn1.charAt(i)));
            if(count % 2 == 1){
                d = n * 2;
                if(d > 9)
                    d -= 9;
                sum += d;
            }else
                sum += n;
            count++;
        }
        return (sum % 10 == 0);
    }

    public String majorIndustryIdentifier(int mii) {
        switch(mii){
            case 1:
            case 2:
                return "Airlines";
            case 3:
                return "Travel";
            case 4:
            case 5:
                return "Banking and Financial";
            case 6:
                return "Merchandising and Banking/Financial";
            case 7:
                return "Petroleum";
            case 8:
                return "Healthcare, Telecommunications";
            case 9:
            case 0:
                return "Unknown";
        }
        return "";
    }

    public String industryIdentificationNumber(int iin) {
        int quo2 = iin / 100;
        int quo3 = iin / 1000;
        int quo4 = iin / 10000;
        int quo5 = iin / 100000;
        if(quo4 >= 34 && quo4 <= 37)
            return "Amex";
        else if(quo5 == 4)
            return "Visa";
        else if(quo4 >= 51 && quo4 <= 55)
            return "Mastercard";
        else if(quo2 == 6011 || quo3 == 644 || quo4 == 65)
            return "Discover";
        else
            return "Unknown";
    }

    public void display() {
        if(vcc) {
            System.out.println(ccn + " is a valid Credit Card Number.");
            System.out.println("Major Industry Identifier(MII) : " + mii);
            System.out.println("Industry Identification Number(IIN) : " + iin);
            System.out.println("Account Number : " + accNo);
            System.out.println("Check Digit : " + cd);
        }
        else{
            int x = getX();
            System.out.println("Invalid Card Number.");
            System.out.println("The Check digit should have been " + x);
        }
    }
    public int getX(){
        int x = (((sum / 10) + 1 ) * 10 - (sum - cd));
        if(x > 9)
            x = ((sum / 10) * 10 - (sum - cd));
        return x;
    }
}