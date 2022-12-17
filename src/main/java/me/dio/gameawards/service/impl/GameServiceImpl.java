package me.dio.gameawards.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.compare.ComparableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dio.gameawards.domain.model.Game;
import me.dio.gameawards.domain.model.GameRepository;
import me.dio.gameawards.service.GameService;
import me.dio.gameawards.service.exception.BusinessException;
import me.dio.gameawards.service.exception.NoContentException;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public List<Game> findAll() {

		List<Game> games = gameRepository.findAll();
		Collections.sort(games, (g1, g2) -> Long.valueOf(g2.getVotes()).compareTo(Long.valueOf(g1.getVotes())));

		return games;
	}

	@Override
	public Game findById(Long id) {
		Optional<Game> game = gameRepository.findById(id);
		return game.orElseThrow(() -> new NoContentException());
	}

	@Override
	public void insert(Game game) {
		gameRepository.save(game);

	}

	@Override
	public void update(Long id, Game game) {
		Game gameDb = findById(id);
		if (gameDb.getId().equals(game.getId())) {
			gameRepository.save(game);
		} else {
			throw new BusinessException("Os IDs para alteração são divergentes...");
		}
	}

	@Override
	public void delete(Long id) {
		gameRepository.delete(findById(id));
	}

	@Override
	public void vote(Long id) {
		Game gameDb = findById(id);
		gameDb.setVotes(gameDb.getVotes() + 1);

		update(id, gameDb);
	}

}
