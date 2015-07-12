package helper;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {
	
	public static String getMac() {
		String output = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			output = sb.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String getTime() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}
	
	public static String getFormatedTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    return sdfDate.format(now);
	}
}
