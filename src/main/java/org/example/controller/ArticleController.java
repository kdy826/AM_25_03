package org.example.controller;

import org.example.dto.Article;
import org.example.util.Util;
import org.example.ArticleManager.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.dto.Member;

public class ArticleController extends Controller {

    private Scanner sc;
    private List<Article> articles;
    private String cmd;

    int lastArticleId = 3;

    List<Member>members = Container.memberDao.members;
    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = Container.articleDao.articles;
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "write":




                doWrite();
                break;
            case "list":
                showList();
                break;
            case "detail":
                showDetail();
                break;
            case "delete":




                doDelete();
                break;
            case "modify":




                doModify();
                break;
            default:
                System.out.println("Unknown action method");
                break;
        }
    }

    private void doWrite() {
        System.out.println("==게시글 작성==");
        int id = lastArticleId + 1;
        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();
        System.out.print("제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("내용 : ");
        String body = sc.nextLine().trim();

        Article article = new Article(id, regDate, updateDate, loginedMember.getId(), title, body);
        articles.add(article);

        System.out.println(id + "번 글이 작성되었습니다");
        lastArticleId++;
    }


    private void showList() {
        System.out.println("==게시글 목록==");
        if (articles.size() == 0) {
            System.out.println("아무것도 없어");
            return;
        }

        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forPrintArticles = articles;

        if (searchKeyword.length() > 0) {
            System.out.println("검색어 : " + searchKeyword);
            forPrintArticles = new ArrayList<>();

            for (Article article : articles) {
                if (article.getTitle().contains(searchKeyword)) {
                    forPrintArticles.add(article);
                }
            }
            if (forPrintArticles.size() == 0) {
                System.out.println("검색 결과 없음");
                return;
            }
        }

        System.out.println("   번호    /     날짜       /   제목     /    내용   /  작성자 ");
        for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
            Article article = forPrintArticles.get(i);
            if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                System.out.printf("  %d   /    %s        /    %s     /    %s    / %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody(), loginedMember);
            } else {
                System.out.printf("  %d   /    %s        /    %s     /    %s   /%s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody(),loginedMember);
            }
        }
    }

    private void showDetail() {
        System.out.println("==게시글 상세보기==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }


//        for(Member member : members){
//            //작성자 이름을 보이게 하고싶어
//        }

        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("작성자 : " + foundArticle.getMemberId());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());


    }

    private void doDelete() {
        System.out.println("==게시글 삭제==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        articles.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다");
    }

    private void doModify() {
        System.out.println("==게시글 수정==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }

        if (foundArticle.getMemberId() != loginedMember.getId()) { // 작성한 게시글 내용을 수정하는데 작성자 아이디와 로그인중인 아이디가 서로 다르면
            System.out.println("권한없음");  // 권한없음 / 수정 .삭제불가능
            return;
        }
        System.out.println("기존 제목 : " + foundArticle.getTitle());
        System.out.println("기존 내용 : " + foundArticle.getBody());
        System.out.print("새 제목 : ");
        String newTitle = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String newBody = sc.nextLine().trim();

        foundArticle.setTitle(newTitle);
        foundArticle.setBody(newBody);

        foundArticle.setUpdateDate(Util.getNowStr());

        System.out.println(id + "번 게시글이 수정되었습니다");
    }


    private Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * 테스트 데이터 생성 함수
     **/
    public void makeTestData() {
        System.out.println("==게시글 테스트 데이터 생성==");
        articles.add(new Article(1, "2024-12-12 12:12:12", "2024-12-12 12:12:12", 1, "제목123", "내용1"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), 1, "제목27", "내용2"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), 2, "제목1233", "내용3"));
    }
}