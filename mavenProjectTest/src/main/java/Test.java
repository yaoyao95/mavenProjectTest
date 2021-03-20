

import java.lang.annotation.Native;
import java.util.UUID;


public class Test {
    @Native static final byte LATIN1 = 0;
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString().replace("-","").toUpperCase());
        //uuid.getMostSignificantBits();
        String s = fastUUID(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
        System.out.println(s);
    }



    private static String fastUUID(long lsb, long msb) {
        byte[] buf = new byte[32];
        formatUnsignedLong0(lsb,        4, buf, 20, 12);
        formatUnsignedLong0(lsb >>> 48, 4, buf, 16, 4);
        formatUnsignedLong0(msb,        4, buf, 12, 4);
        formatUnsignedLong0(msb >>> 16, 4, buf, 8,  4);
        formatUnsignedLong0(msb >>> 32, 4, buf, 0,  8);
        return new String(buf, LATIN1);
    }

    private static void formatUnsignedLong0(long val, int shift, byte[] buf, int offset, int len) {
        int charPos = offset + len;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[--charPos] = (byte)UPPER_DIGITS[((int) val) & mask];
            val >>>= shift;
        } while (charPos > offset);
    }

    private static final char[] DIGITS = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
    private static final char[] UPPER_DIGITS = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'A' , 'B' ,
            'C' , 'D' , 'E' , 'F' , 'G' , 'H' ,
            'I' , 'J' , 'K' , 'L' , 'M' , 'N' ,
            'O' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
            'U' , 'V' , 'W' , 'X' , 'Y' , 'Z'
    };
}
