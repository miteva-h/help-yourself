package dnick.zad5.helpyourself.web;

import dnick.zad5.helpyourself.model.News;
import dnick.zad5.helpyourself.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping( "/home")
    public String showHome(Model model) {
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("/explore/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<News> page = newsService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<News> news = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("news", news);

        model.addAttribute("bodyContent", "explore");
        return "master-template";
    }

    @GetMapping("/explore")
    public String getAllPages(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/dateAfter")
    public String getAllExploredAfterDate(Model model,
                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateAfter) {
        if (dateAfter != null) {
            List<News> news = newsService.listAllByDateAfter(dateAfter);
            model.addAttribute("news", news);
            model.addAttribute("bodyContent", "newsWithDates");
            return "master-template";
        } else {
            return getOnePage(model, 1);
        }
    }

    @GetMapping("/wordContaining")
    public String getAllExploredContainingWord(Model model,
                                               @RequestParam(required = false) String wordContaining) {
        if (!wordContaining.isEmpty()) {
            List<News> news = newsService.listAllByTitleContaining(wordContaining);
            model.addAttribute("news", news);
            model.addAttribute("bodyContent", "newsWithTitles");
            return "master-template";
        } else {
            return getOnePage(model, 1);
        }
    }

    @GetMapping("/advice")
    public String showAdvice(Model model,
                             @RequestParam(required = false) String first,
                             @RequestParam(required = false) String second,
                             @RequestParam(required = false) String third) {
        if (first != null) {
            model.addAttribute("flag", "second");
            model.addAttribute("second", first.equals("stress") ? "stress" : "sadness");
        } else if (second != null) {
            model.addAttribute("flag", "third");
            switch (second) {
                case "family": model.addAttribute("familyList", new ArrayList<>(Arrays.asList("my family doesnt love me", "my family doesnt value me", "my family is too bossy")));
                case "colleagues": model.addAttribute("colleaguesList", new ArrayList<>(Arrays.asList("my colleagues dont communicate with me", "my colleagues dont value me", "my colleagues are too bossy")));
                case "friends": model.addAttribute("friendsList", new ArrayList<>(Arrays.asList("my friends dont love me", "my friends dont value me", "I feel like Im the odd one")));
                case "partner":  model.addAttribute("partnerList", new ArrayList<>(Arrays.asList("my partner doesnt love me", "my partner doesnt value me", "I feel like Im not enough")));
                    model.addAttribute("third", second);
                    break;
                default:
                    break;
            }
        } else if (third != null) {
            model.addAttribute("flag", "fourth");
            switch (third) {
                case "my family doesnt love me":
                case "my family doesnt value me":
                case "my family is too bossy":
                case "my colleagues dont communicate with me":
                case "my colleagues dont value me":
                case "my colleagues are too bossy":
                case "my friends dont love me":
                case "my friends dont value me":
                case "I feel like Im the odd one":
                case "my partner doesnt love me":
                case "my partner doesnt value me":
                case "I feel like Im not enough":
                    model.addAttribute("fourth", third);
                    break;
                default:
                    break;
            }
        } else {
            model.addAttribute("flag", "first");
        }
        model.addAttribute("bodyContent", "advice");
        return "master-template";
    }

    @GetMapping("/diary")
    public String getDiary(Model model, @RequestParam(required = false) String diary) {
        if (diary == null) {
            model.addAttribute("flag", false);
        } else {
            model.addAttribute("flag", true);
            model.addAttribute("date",LocalDate.now());
            model.addAttribute("diary", diary);
        }
        model.addAttribute("bodyContent", "diary");
        return "master-template";
    }

    @GetMapping(path={"/start","/"})
    public String getStart() {
        return "start";
    }

}
