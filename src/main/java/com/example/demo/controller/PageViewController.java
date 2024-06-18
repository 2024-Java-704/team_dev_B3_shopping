package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.entity.Student;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.SaleListRepository;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageViewController {

	@Autowired
	HttpSession session;

	@Autowired
	AccountAndCart accountAndCart;

	@Autowired
	BookinfoRepository bookinfoRepository;

	@Autowired
	SaleListRepository saleListRepository;

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	ImagesRepository imagesRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	//商品一覧画面表示
	@GetMapping("/items")
	public String index(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {

		if (accountAndCart.getId() == null) {
			return "redirect:/login";
		}

		List<SaleList> saleList = saleListRepository.findByItemStatus(1);
		List<Bookinfo> books = new ArrayList<>();

		if (keyword.equals("")) { //キーワードが入力されなかった場合
			for (SaleList sale : saleList) {
				Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();

				//ブックマークされているかを見る
				List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(sale.getId(),
						accountAndCart.getId());
				bookinfo.setBookmark(!bookmark.isEmpty());
				books.add(bookinfo);
			}
		} else { //キーワードが入力された場合
			for (SaleList sale : saleList) {
				List<Bookinfo> bookinfos = bookinfoRepository.findByIdAndTitleContaining(sale.getBookInfoId(), keyword);
				if (!bookinfos.isEmpty()) {

					//ブックマークされているかを見る
					List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(sale.getId(),
							accountAndCart.getId());
					bookinfos.get(0).setBookmark(!bookmark.isEmpty());
					books.add(bookinfos.get(0));
				}
			}

		}
		//繰り返しfor文で画像名をセット
		for (Bookinfo booksImg : books) {
			String bookString = booksImg.getImageId() + "";
			Long bookLong = Long.parseLong(bookString);
			Images image = imagesRepository.findById(bookLong).get();
			booksImg.setImageName(image.getName());
			bookinfoRepository.save(booksImg);
			System.out.println(booksImg.getTitle() + booksImg.isBookmark());
		}

		model.addAttribute("books", books);
		//		for (Bookinfo book : books) {
		//			System.out.println("タイトルは" + book.getTitle());
		//		}

		//仮登録チェック
		if (accountAndCart.getId() != null) {
			Student student = studentRepository.findById(accountAndCart.getId()).get();

			if (student.getStatus() == 5) {
				model.addAttribute("deniedMessage", "申請が却下されました");
				model.addAttribute("Message", "窓口へお問い合わせください");
			}
		}
		return "indexStyleCard";
	}

	//商品詳細画面表示(RequestParam)
	@GetMapping("/items/detail")
	public String detail(@RequestParam("id") Integer id, Model model) {
		Bookinfo book = bookinfoRepository.findById(id).get();

		String imageString = book.getImageId() + "";
		Long imageLong = Long.parseLong(imageString);
		Images image = imagesRepository.findById(imageLong).get();
		book.setImageName(image.getName());
		model.addAttribute("book", book);
		return "detail";
	}

	//商品詳細画面(PathVariable)
	@GetMapping("/items/{id}/detail")
	public String detailPathVariable(
			@PathVariable("id") Integer id,
			Model model) {
		Bookinfo book = bookinfoRepository.findById(id).get();

		String imageString = book.getImageId() + "";
		Long imageLong = Long.parseLong(imageString);
		Images image = imagesRepository.findById(imageLong).get();
		book.setImageName(image.getName());
		model.addAttribute("book", book);
		return "detail";
	}

	//絞り込み機能
	@GetMapping("/items/refine")
	public String itemsRefine(
			@RequestParam(name = "category", defaultValue = "") String[] categories,
			@RequestParam(name = "grade", defaultValue = "0") Integer[] grades,
			@RequestParam(name = "word", defaultValue = "") String keyword,
			Model model) {

		List<SaleList> saleList = saleListRepository.findByItemStatus(1);
		Set<Bookinfo> books = new HashSet<>();

		//カテゴリ
		if (categories != null) {
			for (String category : categories) {
				Categories thisCategory = categoriesRepository.findByCategoryName(category);
				try {
					List<Bookinfo> book = bookinfoRepository.findByCategoryId(thisCategory.getId());
					for (Bookinfo b : book) {
						SaleList s = saleListRepository.findByBookInfoId(b.getId()).get(0);
						if (s.getItemStatus() == 1) {
							//ブックマークされているかを見る
							List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(s.getId(),
									accountAndCart.getId());
							b.setBookmark(!bookmark.isEmpty());
							books.add(b);
						}
					}
				} catch (Exception e) {
					break;
				}

			}
		}

		//学年
		if (grades.length != 0) {
			for (Integer grade : grades) {

				try {
					List<Bookinfo> book = bookinfoRepository.findByGrade(grade);
					for (Bookinfo thisBook : book) {
						SaleList s = saleListRepository.findByBookInfoId(thisBook.getId()).get(0);

						//bookに含まれている書籍情報がbooksに無い+ItemStatus=1の場合	
						if (s.getItemStatus() == 1) {

							//ブックマークされているかを見る
							List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(s.getId(),
									accountAndCart.getId());
							thisBook.setBookmark(!bookmark.isEmpty());

							books.add(thisBook);
						}
					}
				} catch (Exception e) {
					break;
				}
			}

		}

		//キーワード
		if (!(keyword.equals("")))

		{
			List<Bookinfo> thisBooks = bookinfoRepository.findByLectureLike("%" + keyword + "%");
			for (Bookinfo book : thisBooks) {
				SaleList s = saleListRepository.findByBookInfoId(book.getId()).get(0);
				if (s.getItemStatus() == 1) {
					//ブックマークされているかを見る
					List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(s.getId(),
							accountAndCart.getId());
					book.setBookmark(!bookmark.isEmpty());
					books.add(book);
				}

			}

		}

		//キーワードが見つからなかった場合
		if (books.size() == 0)

		{
			for (SaleList sale : saleList) {
				Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();

				//ブックマークされているかを見る
				List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(sale.getId(),
						accountAndCart.getId());
				bookinfo.setBookmark(!bookmark.isEmpty());

				books.add(bookinfo);
			}
			for (Bookinfo booksImg : books) {
				String bookString = booksImg.getImageId() + "";
				Long bookLong = Long.parseLong(bookString);
				Images image = imagesRepository.findById(bookLong).get();
				booksImg.setImageName(image.getName());
				bookinfoRepository.save(booksImg);
				System.out.println(booksImg.getTitle() + booksImg.isBookmark());

				model.addAttribute("nullMessage", "検索した条件に該当する商品は見つかりませんでした");
				model.addAttribute("books", books);

			}
			return "indexStyleCard";
		}

		for (Bookinfo booksImg : books) {
			String bookString = booksImg.getImageId() + "";
			Long bookLong = Long.parseLong(bookString);
			Images image = imagesRepository.findById(bookLong).get();
			booksImg.setImageName(image.getName());
			bookinfoRepository.save(booksImg);
			System.out.println(booksImg.getTitle() + booksImg.isBookmark());
		}

		model.addAttribute("books", books);
		return "indexStyleCard";

	}

	//ブックマーク画面表示
	@GetMapping("/bookmark")
	public String bookMark(Model model) {
		List<Bookmark> bookmark = bookmarkRepository.findByStudentId(accountAndCart.getId());

		List<Bookinfo> books = new ArrayList<>();

		for (Bookmark book : bookmark) {
			SaleList sale = saleListRepository.findById(book.getSalelistId()).get();
			Bookinfo info = bookinfoRepository.findById(sale.getBookInfoId()).get();
			info.setItemStatus(sale.getItemStatus());
			books.add(info);
		}

		model.addAttribute("books", books);
		return "bookmark";
	}

	//ブックマーク追加処理
	@PostMapping("/bookmark/add")
	public String bookMarkAdd(@RequestParam("id") Integer id) {
		List<SaleList> item = saleListRepository.findByBookInfoId(id);
		Integer itemId = item.get(0).getId();
		System.out.println("itemIdは" + itemId);

		List<Bookmark> bookmark = bookmarkRepository.findByStudentId(accountAndCart.getId());
		//		List<Bookmark> bookmark = bookmarkRepository.findAll(); 上手くいかないコード
		for (Bookmark book : bookmark) {

			System.out.println("セールリストIDは" + book.getSalelistId());
			if (itemId == book.getSalelistId()) {
				return "redirect:/bookmark";
			}
		}

		Integer accountId = accountAndCart.getId();
		Bookmark book = new Bookmark(accountId, item.get(0).getId());
		bookmarkRepository.save(book);
		return "redirect:/bookmark";
	}

	//ブックマーク削除処理
	@PostMapping("/bookmark/{id}/delete")
	public String bookMarkDelete(
			@PathVariable("id") Integer id,
			Model model) {
		SaleList saleList = saleListRepository.findByBookInfoId(id).get(0);
		Bookmark bookmark = bookmarkRepository.findByStudentIdAndSalelistId(accountAndCart.getId(), saleList.getId());
		bookmarkRepository.deleteById(bookmark.getId());
		return "redirect:/bookmark";
	}

	//ブックマーク削除処理(GETメソッド)
	@GetMapping("/bookmark/{id}/delete")
	public String bookMarkDeleteGet(
			@PathVariable("id") Integer id,
			Model model) {
		SaleList saleList = saleListRepository.findByBookInfoId(id).get(0);
		Bookmark bookmark = bookmarkRepository.findByStudentIdAndSalelistId(accountAndCart.getId(), saleList.getId());
		bookmarkRepository.deleteById(bookmark.getId());
		return "redirect:/items";
	}

	//ブックマーク追加処理（PathVariable/商品一覧用）
	@GetMapping("/bookmark/{id}/add")
	public String bookMarkAddPath(
			@PathVariable("id") Integer id,
			Model model) {
		List<SaleList> item = saleListRepository.findByBookInfoId(id);
		Integer itemId = item.get(0).getId();
		System.out.println("itemIdは" + itemId);

		List<Bookmark> bookmark = bookmarkRepository.findByStudentId(accountAndCart.getId());
		//		List<Bookmark> bookmark = bookmarkRepository.findAll(); 上手くいかないコード
		for (Bookmark book : bookmark) {

			System.out.println("セールリストIDは" + book.getSalelistId());
			if (itemId == book.getSalelistId()) {
				return "redirect:/items";
			}
		}

		Integer accountId = accountAndCart.getId();
		Bookmark book = new Bookmark(accountId, item.get(0).getId());
		bookmarkRepository.save(book);
		return "redirect:/items";
	}

	//マイページ画面表示
	@GetMapping("/mypage")
	public String mypage(Model model) {
		try {
			Integer accountId = accountAndCart.getId();
			Student student = studentRepository.findById(accountId).get();

			model.addAttribute("student", student);
			return "mypage";
		} catch (Exception E) {
			return "/login";
		}

	}

	//学生証の確認画面
	@GetMapping("/studentCardUp")
	public String cardPage(Model model) {

		try {
			//セッションID→String変換→Long変換→検索→セット
			Student student = studentRepository.findById(accountAndCart.getId()).get();
			String imageString = student.getImageId() + "";
			Long image = Long.parseLong(imageString);
			Images imageName = imagesRepository.findById(image).get();
			student.setImageName(imageName.getName());
			studentRepository.save(student);

			model.addAttribute("student", student);
			return "Upload-CardView";
		} catch (Exception E) {
			Student student = studentRepository.findById(accountAndCart.getId()).get();
			model.addAttribute("student", student);
			return "Upload-CardView";
		}
	}

}
