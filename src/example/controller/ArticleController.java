package example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import example.dto.Article;
import example.util.Util;

public class ArticleController extends Controller {
	
	private List<Article> articles;
	
	private int lastArticleId;
	
	public ArticleController(Scanner sc) {
		this.articles = new ArrayList<>();
		this.lastArticleId = 0;
		this.sc = sc;
		this.cmd = null;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch (methodName) {
		case "write" :
			doWrite();
			break;
		case "list" :
			showList();
			break;
		case "detail" :
			showDetail();
			break;
		case "modify" :
			doModify();
			break;
		case "delete" :
			doDelete();
			break;
		default :
			System.out.println("존재하지 않는 명령어 입니다");
			break;
		}
	}
			
	private void doWrite() {
		lastArticleId++;
		
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		
		this.articles.add(new Article(lastArticleId, Util.getDateStr(), title, body));
		
		System.out.println(lastArticleId + "번 게시물이 생성되었습니다\n");
	}
	
	private void showList() {
		if (this.articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다");
			return;
		}
		
		String searchKeyword = this.cmd.substring("article list".length()).trim();
		
		List<Article> printArticles = this.articles;
		
		if (searchKeyword.length() > 0) {
			
			System.out.println("검색어 : " + searchKeyword);
			
			printArticles = new ArrayList<>();
			
			for (Article article : this.articles) {
				if (article.title.contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
		}
		
		if (printArticles.size() == 0) {
			System.out.println("검색결과가 없습니다");
			return;
		}
			
		System.out.println("번호	/	제목	/		작성일");
		for (int i = 0; i < printArticles.size(); i++) {
			Article article = printArticles.get(printArticles.size() - 1 - i);
			System.out.printf("%d	/	%s	/	%s\n", article.id, article.title, article.regDate);
		}
	}
	
	private void showDetail() {
		String[] cmdBits = this.cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("작성일 : %s\n", foundArticle.regDate);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
	}
	
	private void doModify() {
		String[] cmdBits = this.cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine();
		
		foundArticle.title = title;
		foundArticle.body = body;
		System.out.printf("%d번 게시물을 수정했습니다\n", id);
	}
	
	private void doDelete() {
		String[] cmdBits = this.cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("명령어를 확인해주세요");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		this.articles.remove(foundArticle);
		System.out.printf("%d번 게시물을 삭제했습니다\n", id);
	}
	
	private Article getArticleById(int id) {
		for (Article article : this.articles) {
			if (article.id == id) {
				return article;
			}
		}
		
		return null;
	}
	
	@Override
	public void makeTestData() {
		this.articles.add(new Article(++lastArticleId, Util.getDateStr(), "제목1", "내용1"));
		this.articles.add(new Article(++lastArticleId, Util.getDateStr(), "제목2", "내용2"));
		this.articles.add(new Article(++lastArticleId, Util.getDateStr(), "제목3", "내용3"));
		System.out.println("테스트용 게시물이 생성되었습니다");
	}
}
