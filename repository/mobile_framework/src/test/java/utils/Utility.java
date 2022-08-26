package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	public static String millisToTimeConversion(long seconds) {
		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;
		int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
		seconds -= minutes * SECONDS_IN_A_MINUTE;
		int hours = minutes / MINUTES_IN_AN_HOUR;
		minutes -= hours * MINUTES_IN_AN_HOUR;
		return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int) seconds);
	}

	private static String prefixZeroToDigit(int num) {
		int number = num;
		if (number <= 9) {
			String sNumber = "0" + number;
			return sNumber;
		} else
			return "" + number;
	}

	public static String formatTime(long time) {
		DateFormat formatter = new SimpleDateFormat("kk:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime());
	}

	public static String getStackStrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	protected String getExceptionMessage(Throwable e) {
		if (null != e.getMessage()) {
			return e.getMessage();
		} else {
			return "";
		}
	}

	public static Properties readPropertyFile(String fileName) throws Exception {
		Properties localeFileprop = new Properties();
		FileInputStream in = null;
		InputStreamReader reader = null;
		in = new FileInputStream(new File(fileName));
		reader = new InputStreamReader(in, "UTF-8");
		localeFileprop.load(reader);
		return localeFileprop;
	}

	public static String getTimeStamp() {
		return (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date());
	}

	/**
	 * @author dishant.doshi
	 * @param node1 top node
	 * @param node2 lower node
	 * @return node2 value
	 * @creation date 27/09/2018
	 */
	public static String getTestData(String fileName, String node1, String node2) {
		return new ReadXMLData(fileName).get(node1, node2);
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The difference
	 * between min and max can be at most <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min Minimum value
	 * @param max Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int getRandomInt(int min, int max) {

		// Usually this should be a field rather than a method variable so
		// that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String getStringFromArray(String[] arr) {
		String str = "";
		for (int i = 0; i < arr.length; i++) {
			str += arr[i] + ",";
		}
		if (str.endsWith(","))
			str = str.substring(0, str.length() - 1);
		return str;
	}

	public static void appendTextToFile(String filePath, String text) {

		try {
			File fileObj = new File(filePath);
			fileObj.getParentFile().mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
			out.println(text);
		} catch (IOException e) {
			System.out.println("Error while writing content to file '" + filePath + "'");
			e.printStackTrace();
		}
	}

	public static String getDiffInString(Date startDate, Date endDate) {

		// milliseconds
		long different = endDate.getTime() - startDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		// long daysInMilli = hoursInMilli * 24;

		// long elapsedDays = different / daysInMilli;
		// different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		String diffInString = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
		return diffInString;
	}

	public static String generatRandomString(int stringLen) {
		String randomString = RandomStringUtils.random(stringLen, true, true);
		return randomString;
	}

	public static void executeCMDCommand(String command) {
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void removeFolder(String file) {
		File rmFile = new File(file);
		File[] contents = rmFile.listFiles();
		if (contents != null) {
			for (File f : contents) {
				String deleteFile = f.toString();
				removeFolder(deleteFile);
			}
		}
		rmFile.delete();
	}

	public static void moveFolder(String source, String destination) {
		Path sourcePath = Paths.get(source);
		Path destinationPath = Paths.get(destination);
		Path destinationHistoryPath = Paths.get(destination + "/history");
		boolean exist = Files.exists(destinationHistoryPath, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		if (!exist) {
			try {
				Files.move(sourcePath, destinationPath.resolve(sourcePath.getFileName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			List<String> sourceHistoryFiles = getFiles(source);
			for (int i = 0; i < sourceHistoryFiles.size(); i++)
				overwriteFile(source + File.separator + sourceHistoryFiles.get(i),
						destinationHistoryPath + File.separator + sourceHistoryFiles.get(i));
		}
	}

	public static void overwriteFile(String source, String destination) {
		FileReader fr = null;
		int i;
		String string = "";
		try {
			fr = new FileReader(source);
			while ((i = fr.read()) != -1)
				string = string + ((char) i);
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			File file = new File(destination);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(string);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getFiles(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> files = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile())
				files.add(listOfFiles[i].getName());
		}
		return files;
	}

	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		return dateTime;
	}

	public static String getCurrentDateTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		return dateTime;
	}

	public static String getCurrentTime() {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String dateTime = timeFormat.format(date);
		return dateTime;
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getCurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String readJSFile(String search, String fileName) {
		if (fileName.contains("en-US.js"))
			search = "'" + search + "'";
		String[] searchString = null;
		String result = "";
		Path path = Paths.get(fileName);
		try (Scanner scanner = new Scanner(path)) {
			while (scanner.hasNextLine()) {
				searchString = scanner.nextLine().split(":");
				if (searchString[0].trim().equalsIgnoreCase(search.trim()))
					result = searchString[1].trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.substring(0, result.length() - 1).replaceAll("'", "");
	}

	public static List<Object> getMapKeys(Map<Object, Object> map) {
		Set<Object> keySet = map.keySet();
		List<Object> keys = new ArrayList<Object>(keySet);
		return keys;
	}

	public static boolean compareTwoLists(List<String> list1, List<String> list2) {
		return list1.toString().contentEquals(list2.toString()) ? true : false;
	}

	public static void copyFile(String inputFile, String outputFile) {
		File in = new File(inputFile);
		File out = new File(outputFile);
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getJsonStringFromMap(Map<Object, Object> map) {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonResp = null;
		try {
			jsonResp = mapperObj.writeValueAsString(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResp;
	}

	@SuppressWarnings("unchecked")
	public static Map<Object, Object> getMapFromJsonString(String data) {
		ObjectMapper mapper = new ObjectMapper();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {
			dataMap = mapper.readValue(data, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	public static String getPreviousOrNextDate(int days) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate;
		Date newDate = null;
		try {
			myDate = dateFormat.parse(getCurrentDate("yyyy-MM-dd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			calendar.add(Calendar.DAY_OF_YEAR, (days));
			newDate = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateFormat.format(newDate);
	}

	public static boolean isOnlyDigits(String str) {
		boolean status = false;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				status = true;
			} else {
				return false;
			}
		}
		return status;
	}

	public static String fetchOnlyDigits(String string) {
		string = string.split("\\.")[0];
		return string.replaceAll("[^0-9]", "");
	}

	public static void main(String[] args) {
		System.out.println(fetchOnlyDigits("500.00"));
	}
}