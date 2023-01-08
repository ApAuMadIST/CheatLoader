package net.minecraft.src;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public final class J_SajParser {
	public void func_27463_a(Reader reader1, J_JsonListener j_JsonListener2) throws J_InvalidSyntaxException, IOException {
		J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader3 = new J_PositionTrackingPushbackReader(reader1);
		char c4 = (char)j_PositionTrackingPushbackReader3.func_27333_c();
		switch(c4) {
		case '[':
			j_PositionTrackingPushbackReader3.func_27334_a(c4);
			j_JsonListener2.func_27195_b();
			this.func_27455_a(j_PositionTrackingPushbackReader3, j_JsonListener2);
			break;
		case '{':
			j_PositionTrackingPushbackReader3.func_27334_a(c4);
			j_JsonListener2.func_27195_b();
			this.func_27453_b(j_PositionTrackingPushbackReader3, j_JsonListener2);
			break;
		default:
			throw new J_InvalidSyntaxException("Expected either [ or { but got [" + c4 + "].", j_PositionTrackingPushbackReader3);
		}

		int i5 = this.func_27448_l(j_PositionTrackingPushbackReader3);
		if(i5 != -1) {
			throw new J_InvalidSyntaxException("Got unexpected trailing character [" + (char)i5 + "].", j_PositionTrackingPushbackReader3);
		} else {
			j_JsonListener2.func_27204_c();
		}
	}

	private void func_27455_a(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1, J_JsonListener j_JsonListener2) throws J_InvalidSyntaxException, IOException {
		char c3 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
		if(c3 != 91) {
			throw new J_InvalidSyntaxException("Expected object to start with [ but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		} else {
			j_JsonListener2.func_27200_d();
			char c4 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
			j_PositionTrackingPushbackReader1.func_27334_a(c4);
			if(c4 != 93) {
				this.func_27464_d(j_PositionTrackingPushbackReader1, j_JsonListener2);
			}

			boolean z5 = false;

			while(!z5) {
				char c6 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
				switch(c6) {
				case ',':
					this.func_27464_d(j_PositionTrackingPushbackReader1, j_JsonListener2);
					break;
				case ']':
					z5 = true;
					break;
				default:
					throw new J_InvalidSyntaxException("Expected either , or ] but got [" + c6 + "].", j_PositionTrackingPushbackReader1);
				}
			}

			j_JsonListener2.func_27197_e();
		}
	}

	private void func_27453_b(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1, J_JsonListener j_JsonListener2) throws J_InvalidSyntaxException, IOException {
		char c3 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
		if(c3 != 123) {
			throw new J_InvalidSyntaxException("Expected object to start with { but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		} else {
			j_JsonListener2.func_27194_f();
			char c4 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
			j_PositionTrackingPushbackReader1.func_27334_a(c4);
			if(c4 != 125) {
				this.func_27449_c(j_PositionTrackingPushbackReader1, j_JsonListener2);
			}

			boolean z5 = false;

			while(!z5) {
				char c6 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
				switch(c6) {
				case ',':
					this.func_27449_c(j_PositionTrackingPushbackReader1, j_JsonListener2);
					break;
				case '}':
					z5 = true;
					break;
				default:
					throw new J_InvalidSyntaxException("Expected either , or } but got [" + c6 + "].", j_PositionTrackingPushbackReader1);
				}
			}

			j_JsonListener2.func_27203_g();
		}
	}

	private void func_27449_c(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1, J_JsonListener j_JsonListener2) throws J_InvalidSyntaxException, IOException {
		char c3 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
		if(34 != c3) {
			throw new J_InvalidSyntaxException("Expected object identifier to begin with [\"] but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		} else {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			j_JsonListener2.func_27205_a(this.func_27452_i(j_PositionTrackingPushbackReader1));
			char c4 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
			if(c4 != 58) {
				throw new J_InvalidSyntaxException("Expected object identifier to be followed by : but got [" + c4 + "].", j_PositionTrackingPushbackReader1);
			} else {
				this.func_27464_d(j_PositionTrackingPushbackReader1, j_JsonListener2);
				j_JsonListener2.func_27199_h();
			}
		}
	}

	private void func_27464_d(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1, J_JsonListener j_JsonListener2) throws J_InvalidSyntaxException, IOException {
		char c3 = (char)this.func_27448_l(j_PositionTrackingPushbackReader1);
		switch(c3) {
		case '\"':
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			j_JsonListener2.func_27198_c(this.func_27452_i(j_PositionTrackingPushbackReader1));
			break;
		case '-':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			j_JsonListener2.func_27201_b(this.func_27459_a(j_PositionTrackingPushbackReader1));
			break;
		case '[':
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			this.func_27455_a(j_PositionTrackingPushbackReader1, j_JsonListener2);
			break;
		case 'f':
			char[] c6 = new char[4];
			int i7 = j_PositionTrackingPushbackReader1.func_27336_b(c6);
			if(i7 == 4 && c6[0] == 97 && c6[1] == 108 && c6[2] == 115 && c6[3] == 101) {
				j_JsonListener2.func_27193_j();
				break;
			}

			j_PositionTrackingPushbackReader1.func_27335_a(c6);
			throw new J_InvalidSyntaxException("Expected \'f\' to be followed by [[a, l, s, e]], but got [" + Arrays.toString(c6) + "].", j_PositionTrackingPushbackReader1);
		case 'n':
			char[] c8 = new char[3];
			int i9 = j_PositionTrackingPushbackReader1.func_27336_b(c8);
			if(i9 != 3 || c8[0] != 117 || c8[1] != 108 || c8[2] != 108) {
				j_PositionTrackingPushbackReader1.func_27335_a(c8);
				throw new J_InvalidSyntaxException("Expected \'n\' to be followed by [[u, l, l]], but got [" + Arrays.toString(c8) + "].", j_PositionTrackingPushbackReader1);
			}

			j_JsonListener2.func_27202_k();
			break;
		case 't':
			char[] c4 = new char[3];
			int i5 = j_PositionTrackingPushbackReader1.func_27336_b(c4);
			if(i5 != 3 || c4[0] != 114 || c4[1] != 117 || c4[2] != 101) {
				j_PositionTrackingPushbackReader1.func_27335_a(c4);
				throw new J_InvalidSyntaxException("Expected \'t\' to be followed by [[r, u, e]], but got [" + Arrays.toString(c4) + "].", j_PositionTrackingPushbackReader1);
			}

			j_JsonListener2.func_27196_i();
			break;
		case '{':
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			this.func_27453_b(j_PositionTrackingPushbackReader1, j_JsonListener2);
			break;
		default:
			throw new J_InvalidSyntaxException("Invalid character at start of value [" + c3 + "].", j_PositionTrackingPushbackReader1);
		}

	}

	private String func_27459_a(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(45 == c3) {
			stringBuilder2.append('-');
		} else {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
		}

		stringBuilder2.append(this.func_27451_b(j_PositionTrackingPushbackReader1));
		return stringBuilder2.toString();
	}

	private String func_27451_b(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(48 == c3) {
			stringBuilder2.append('0');
			stringBuilder2.append(this.func_27462_f(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27454_g(j_PositionTrackingPushbackReader1));
		} else {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
			stringBuilder2.append(this.func_27460_c(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27456_e(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27462_f(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27454_g(j_PositionTrackingPushbackReader1));
		}

		return stringBuilder2.toString();
	}

	private char func_27460_c(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		switch(c3) {
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return c3;
		default:
			throw new J_InvalidSyntaxException("Expected a digit 1 - 9 but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		}
	}

	private char func_27458_d(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		switch(c3) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return c3;
		default:
			throw new J_InvalidSyntaxException("Expected a digit 1 - 9 but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		}
	}

	private String func_27456_e(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException {
		StringBuilder stringBuilder2 = new StringBuilder();
		boolean z3 = false;

		while(!z3) {
			char c4 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
			switch(c4) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				stringBuilder2.append(c4);
				break;
			default:
				z3 = true;
				j_PositionTrackingPushbackReader1.func_27334_a(c4);
			}
		}

		return stringBuilder2.toString();
	}

	private String func_27462_f(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(c3 == 46) {
			stringBuilder2.append('.');
			stringBuilder2.append(this.func_27458_d(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27456_e(j_PositionTrackingPushbackReader1));
		} else {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
		}

		return stringBuilder2.toString();
	}

	private String func_27454_g(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(c3 != 46 && c3 != 69) {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
		} else {
			stringBuilder2.append('E');
			stringBuilder2.append(this.func_27461_h(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27458_d(j_PositionTrackingPushbackReader1));
			stringBuilder2.append(this.func_27456_e(j_PositionTrackingPushbackReader1));
		}

		return stringBuilder2.toString();
	}

	private String func_27461_h(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(c3 != 43 && c3 != 45) {
			j_PositionTrackingPushbackReader1.func_27334_a(c3);
		} else {
			stringBuilder2.append(c3);
		}

		return stringBuilder2.toString();
	}

	private String func_27452_i(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws J_InvalidSyntaxException, IOException {
		StringBuilder stringBuilder2 = new StringBuilder();
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		if(34 != c3) {
			throw new J_InvalidSyntaxException("Expected [\"] but got [" + c3 + "].", j_PositionTrackingPushbackReader1);
		} else {
			boolean z4 = false;

			while(!z4) {
				char c5 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
				switch(c5) {
				case '\"':
					z4 = true;
					break;
				case '\\':
					char c6 = this.func_27457_j(j_PositionTrackingPushbackReader1);
					stringBuilder2.append(c6);
					break;
				default:
					stringBuilder2.append(c5);
				}
			}

			return stringBuilder2.toString();
		}
	}

	private char func_27457_j(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		char c3 = (char)j_PositionTrackingPushbackReader1.func_27333_c();
		char c2;
		switch(c3) {
		case '\"':
			c2 = 34;
			break;
		case '/':
			c2 = 47;
			break;
		case '\\':
			c2 = 92;
			break;
		case 'b':
			c2 = 8;
			break;
		case 'f':
			c2 = 12;
			break;
		case 'n':
			c2 = 10;
			break;
		case 'r':
			c2 = 13;
			break;
		case 't':
			c2 = 9;
			break;
		case 'u':
			c2 = (char)this.func_27450_k(j_PositionTrackingPushbackReader1);
			break;
		default:
			throw new J_InvalidSyntaxException("Unrecognised escape character [" + c3 + "].", j_PositionTrackingPushbackReader1);
		}

		return c2;
	}

	private int func_27450_k(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException, J_InvalidSyntaxException {
		char[] c2 = new char[4];
		int i3 = j_PositionTrackingPushbackReader1.func_27336_b(c2);
		if(i3 != 4) {
			throw new J_InvalidSyntaxException("Expected a 4 digit hexidecimal number but got only [" + i3 + "], namely [" + String.valueOf(c2, 0, i3) + "].", j_PositionTrackingPushbackReader1);
		} else {
			try {
				int i4 = Integer.parseInt(String.valueOf(c2), 16);
				return i4;
			} catch (NumberFormatException numberFormatException6) {
				j_PositionTrackingPushbackReader1.func_27335_a(c2);
				throw new J_InvalidSyntaxException("Unable to parse [" + String.valueOf(c2) + "] as a hexidecimal number.", numberFormatException6, j_PositionTrackingPushbackReader1);
			}
		}
	}

	private int func_27448_l(J_PositionTrackingPushbackReader j_PositionTrackingPushbackReader1) throws IOException {
		boolean z3 = false;

		int i2;
		do {
			i2 = j_PositionTrackingPushbackReader1.func_27333_c();
			switch(i2) {
			case 9:
			case 10:
			case 13:
			case 32:
				break;
			default:
				z3 = true;
			}
		} while(!z3);

		return i2;
	}
}
