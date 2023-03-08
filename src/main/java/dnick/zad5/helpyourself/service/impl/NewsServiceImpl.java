package dnick.zad5.helpyourself.service.impl;

import dnick.zad5.helpyourself.model.News;
import dnick.zad5.helpyourself.model.exceptions.InvalidNewsIdException;
import dnick.zad5.helpyourself.repository.NewsRepository;
import dnick.zad5.helpyourself.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    public Page<News> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return newsRepository.findAll(pageable);
    }

    @Override
    public List<News> listAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
    }

    @Override
    public News create(String url, String title, String photoUrl, LocalDate date) {
        return newsRepository.save(new News(url, title, photoUrl, date));
    }

    @Override
    public News update(Long id, String url, String title, String photoUrl, LocalDate date) {
        News n = newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
        n.setUrl(url);
        n.setTitle(title);
        n.setPhotoUrl(photoUrl);
        n.setDate(date);
        newsRepository.save(n);
        return n;
    }

    @Override
    public News delete(Long id) {
        News n = newsRepository.findById(id).orElseThrow(InvalidNewsIdException::new);
        newsRepository.deleteById(id);
        return n;
    }

    @Override
    public List<News> listAllByDateAfter(LocalDate dateAfter) {
        return newsRepository.findAllByDateAfter(dateAfter);
    }

    @Override
    public List<News> listAllByTitleContaining(String word) {
        return newsRepository.findAllByTitleContainingIgnoreCase(word);
    }
}
