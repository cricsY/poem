import os
import jieba
import matplotlib.pyplot as plt
from wordcloud import WordCloud

path = os.path.dirname(os.getcwd())+"\\dataset_Pretreatment1\\"
files = os.listdir(path)

for file in files:
    txt_path = path + file

    # 读取txt文件
    content = open(txt_path, 'r',errors='ignore').read()
    # 使用Jieba进行分词
    jieba_cut = " ".join(jieba.cut(content, cut_all=False))
    # 字体
    font =os.getcwd()+"\\Font\\WeiRuanZhengHei.ttc"
    # 停用词
    stopwords = {}.fromkeys([line.rstrip() for line in open(os.getcwd()+"\\StopWord.txt")])

    final = ''
    for seg in jieba_cut:
        if seg not in stopwords:
            final =final+seg

    jieba_cut2 = " ".join(jieba.cut(final, cut_all=False))

    # 词云准备
    cloud = WordCloud(
        max_words=100,
        background_color="white",
        #mask=backgroud,
        font_path=font,
        width=600,
        height=460,
        margin=2)

    # 生成词云
    word_cloud = cloud.generate(jieba_cut)

    # 存储词云
    cloud.to_file(os.getcwd()+"\\wordcloud\\" + file.replace("txt", "png"))

print("生成完成！")
