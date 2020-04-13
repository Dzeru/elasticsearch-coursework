package com.dzeru.elasticsearchcoursework;

import com.dzeru.elasticsearchcoursework.processors.pipelines.RussianStemmerPipeline;
import com.dzeru.elasticsearchcoursework.services.impl.HabrDocumentExtractorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchCourseworkApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private HabrDocumentExtractorImpl documentExtractor;

	/*@Test
	public void test1() throws Exception {
		String[] args = new String[1];
		args[0] = "247373";
		documentExtractor.extractDocument(args);
	}
*/

	@Autowired
	RussianStemmerPipeline russianStemmerPipeline;

	@Test
	public void test2() throws Exception {
		String s = "<div class=\"post__text post__text-html js-mediator-article\" id=\"post-content-body\" data-io-article-url=\"https://habr.com/ru/post/247373/\"><blockquote><b>пндексы</b> — это первое, что необходимо хорошо понимать в работе <i>SQL Server</i>, но странным образом базовые вопросы не слишком часто задаются на форумах и получают не так уж много ответов.<br/><i>Роб Шелдон</i> отвечает на эти, вызывающие смущение в профессиональных кругах, вопросы об индексах в <i>SQL Server</i>: одни из них мы просто стесняемся задать, а прежде чем задать другие сначала подумаем дважды.</blockquote> <br/><br/><div class=\"spoiler\"><b class=\"spoiler_title\">От переводчика</b><div class=\"spoiler_text\">Данный пост является компиляцией двух статей Роба Шелдона:<br/><ul><li><a href=\"https://www.simple-talk.com/sql/learn-sql-server/sql-server-index-basics/\">SQL Server Index Basics</a> от 25 ноября 2008 года (заметка даёт понимание основных терминов)</li><li><a href=\"https://www.simple-talk.com/sql/performance/14-sql-server-indexing-questions-you-were-too-shy-to-ask/\">14 SQL Server Indexing Questions You Were Too Shy To Ask</a> от 25 марта 2014 года (собственно, ради неё всё и затевалось)</li></ul><br/>Если вы пишите запросы на языке <i>T-SQL</i>, но плохо понимаете откуда берутся данные, то стоит прочитать данный перевод.<br/>Если же вы захотите знать больше, то в конце перевода я даю тройку книг с которых следует двигаться дальше.</div></div><br/><a name=\"habracut\"></a><br/><div class=\"spoiler\"><b class=\"spoiler_title\">пспользуемая терминология в русском переводе</b><div class=\"spoiler_text\"><table><tr><td>index</td><td>индекс</td></tr><tr><td>heap</td><td>куча</td></tr><tr><td>table</td><td>таблица</td></tr><tr><td>view</td><td>представление</td></tr><tr><td>B-tree</td><td>сбалансированное дерево</td></tr><tr><td>clustered index</td><td>кластеризованный индекс</td></tr><tr><td>nonclustered index</td><td>некластеризованный индекс</td></tr><tr><td>composite index</td><td>составной индекс</td></tr><tr><td>covering index</td><td>покрывающий индекс</td></tr><tr><td>primary key constraint</td><td>ограничение на первичный ключ</td></tr><tr><td>unique constraint</td><td>ограничение на уникальность значений </td></tr><tr><td>query</td><td>запрос</td></tr><tr><td>query engine</td><td>подсистема запросов</td></tr><tr><td>database</td><td>база данных</td></tr><tr><td>database engine</td><td>подсистема хранения данных</td></tr><tr><td>fill factor</td><td>коэффициент заполнения индекса</td></tr><tr><td>surrogate primary key</td><td>суррогатный первичный ключ</td></tr><tr><td>query optimizer</td><td>оптимизатор запросов</td></tr><tr><td>index selectivity</td><td>избирательность индекса</td></tr><tr><td>filtered index</td><td>фильтруемый индекс</td></tr><tr><td>execution plan</td><td>план выполнения</td></tr></table></div></div><br/> <br/><a href=\"#subj\">Пропустить чтение основ и сразу перейти к 14 вопросам</a><br/><br/><h1>Основы индексов в SQL Server</h1><br/>Одним из важнейших путей достижения высокой производительности <i>SQL Server</i> является использование индексов. пндекс ускоряет процесс запроса, предоставляя быстрый доступ к строкам данных в таблице, аналогично тому, как указатель в книге помогает вам быстро найти необходимую информацию. В этой статье я приведу краткий обзор индексов в <i>SQL Server</i> и объясню как они организованы в базе данных и как они помогают ускорению выполнения запросов к базе данных.<br/><br/><h3>Структура индекса</h3><br/>пндексы создаются для столбцов таблиц и представлений. пндексы предоставляют путь для быстрого поиска данных на основе значений в этих столбцах. Например, если вы создадите индекс по первичному ключу, а затем будете искать строку с данными, используя значения первичного ключа, то <i>SQL Server</i> сначала найдет значение индекса, а затем использует индекс для быстрого нахождения всей строки с данными. Без индекса будет выполнен полный просмотр (сканирование) всех строк таблицы, что может оказать значительное влияние на производительность.<br/>Вы можете создать индекс на большинстве столбцов таблицы или представления. псключением, преимущественно, являются столбцы с типами данных для хранения больших объектов (<i>LOB</i>), таких как <i>image</i>, <i>text </i>или <i>varchar(max)</i>. Вы также можете создать индексы на столбцах, предназначенных для хранения данных в формате <i>XML</i>, но эти индексы устроены немного иначе, чем стандартные и их рассмотрение выходит за рамки данной статьи. Также в статье не рассматриваются <i>columnstore </i>индексы. Вместо этого я фокусируюсь на тех индексах, которые наиболее часто применяются в базах данных <i>SQL Server</i>.<br/>пндекс состоит из набора страниц, узлов индекса, которые организованы в виде древовидной структуры — <i>сбалансированного дерева</i>. Эта структура является иерархической по своей природе и начинается с корневого узла на вершине иерархии и конечных узлов, листьев, в нижней части, как показано на рисунке:<br/><img src=\"https://habrastorage.org/getpro/habr/post_images/e35/b8e/5a8/e35b8e5a8538064b22082a4e5351574a.jpg\" alt=\"Структура индекса\"/><br/>";
		System.out.println(russianStemmerPipeline.process(s));
	}

}

