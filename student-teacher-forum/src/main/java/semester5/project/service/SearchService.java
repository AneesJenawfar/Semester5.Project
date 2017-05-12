package semester5.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import semester5.project.model.dto.SearchResult;
import semester5.project.model.entity.Profile;
import semester5.project.model.repository.ProfileDao;

@Service
public class SearchService {

	@Autowired
	private ProfileDao profileDao;

	@Value("${results.pagesize}")
	private int pageSize;

	public Page<SearchResult> searchByInterest(String text, int pageNumber) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize);
		Page<Profile> results = profileDao.findByInterestsNameContainingIgnoreCase(text, request);

		Converter<Profile, SearchResult> converter = new Converter<Profile, SearchResult>() {

			public SearchResult convert(Profile profile) {

				return new SearchResult(profile);
			}
		};
		return results.map(converter);
	}

	public Page<SearchResult> searchBySurname(String text, int pageNumber) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize);
		Page<Profile> results = profileDao.findByUserSurnameContainingIgnoreCase(text, request);

		Converter<Profile, SearchResult> converter = new Converter<Profile, SearchResult>() {

			public SearchResult convert(Profile profile) {

				return new SearchResult(profile);
			}
		};
		return results.map(converter);
	}

	public Page<SearchResult> search(String text, int pageNumber) {

		PageRequest request = new PageRequest(pageNumber - 1, pageSize);
		Page<Profile> results = profileDao.findByUserFirstnameContainingIgnoreCase(text, request);

		Converter<Profile, SearchResult> converter = new Converter<Profile, SearchResult>() {

			public SearchResult convert(Profile profile) {

				return new SearchResult(profile);
			}
		};
		return results.map(converter);
	}
}
