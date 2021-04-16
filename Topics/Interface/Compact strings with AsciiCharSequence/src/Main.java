import java.util.Arrays;

class AsciiCharSequence implements CharSequence {

    byte[] content;

    public AsciiCharSequence(byte[] input) {
        content = new byte[input.length];
        System.arraycopy(input, 0, content, 0, input.length);
    }

    @Override
    public int length() {
        return content.length;
    }

    @Override
    public char charAt(int i) {
        return (char) content[i];
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        return new AsciiCharSequence(Arrays.copyOfRange(content, i, i1));
    }

    @Override
    public String toString() {
        return new String(content);
    }
}