package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.spreada.utils.chinese.ZHConverter;

import entity.Poem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Pretreatment {
	static boolean EditDataByAuthor(String fl) throws IOException{
		boolean result = false;
		File file = new File(fl);
		if (!file.exists()) {
			System.out.println("无法获取相应文件");
			return result;
		}

		StringBuffer strbuffer = new StringBuffer();
		try {
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader in = new BufferedReader(inputStreamReader);

			String str;
			while ((str = in.readLine()) != null) {
				strbuffer.append(str);
			}

			// System.out.println(strbuffer);

			JSONArray array = JSONArray.fromObject(strbuffer.toString());

			List<Poem> list = new ArrayList<Poem>();
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				// Poem poem = (Poem) JSONObject.toBean(jsonObject, Poem.class); //
				// 通过JSONObject.toBean()方法进行对象间的转换
				Poem poem = new Poem();
				
				ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
				
				poem.setAuthor(converter.convert(jsonObject.getString("author")));
				poem.setParagraphs(converter.convert(jsonObject.getString("paragraphs")));
				list.add(poem);
			}
			//-System.out.println(list.size() + list.get(0).getParagraphs());

			for (int i = 0; i < list.size(); i++) {
				Poem poem = list.get(i);
				FileWriter writer = null;

				// 检查文件夹是否存在，不存在则创建文件夹
				String FilePath = "../dataset_Pretreatment";
				File dir = new File(FilePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// 检查文件是否存在，不存在则创建文件
				File checkFile = new File(dir + "\\total.txt");
				if (!checkFile.exists()) {
					checkFile.createNewFile();
				}

				writer = new FileWriter(checkFile, true);
				writer.append(poem.getParagraphs());
				writer.flush();

				writer.close();
			}

			in.close();
			inputStreamReader.close();
			inputStream.close();

		} catch (IOException e) {
			System.out.println(e);
		}
		return true;
	}
}
