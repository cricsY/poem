package Util;

import java.io.File;
import java.io.IOException;

public class TextOperation {

	public static void main(String[] args) throws IOException {
		for (int i = 0; i <= Constant.MAXLENGTH; i = i + 1000) {
			String FileName = Constant.BasePath + String.valueOf(i) + ".json";
			if (!Pretreatment.EditDataByAuthor(FileName)) {
				System.out.println(FileName + "�����ݼ�����ʧ�ܣ���ֹ����!\n");
				break;
			}
			System.out.println(i + "�����ݼ�����ɹ���");
		}

	}
}
