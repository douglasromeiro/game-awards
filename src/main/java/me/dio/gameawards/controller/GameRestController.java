package me.dio.gameawards.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import me.dio.gameawards.controller.games.BaseRestController;
import me.dio.gameawards.domain.model.Game;
import me.dio.gameawards.service.GameService;
import me.dio.gameawards.service.exception.BusinessException;

@CrossOrigin
@RestController
public class GameRestController extends BaseRestController {

	@Autowired
	private GameService businessLayer;

	@GetMapping("games")
	public ResponseEntity<List<Game>> findAll() {
		return ResponseEntity.ok(businessLayer.findAll());
	}

	@GetMapping("games/{id}")
	public ResponseEntity<Game> findById(@PathVariable Long id) {
		return ResponseEntity.ok(businessLayer.findById(id));
	}

	@PostMapping("games")
	public ResponseEntity<Game> insert(@RequestBody Game game) {
		if(Objects.nonNull(game.getId())) {
			throw new BusinessException("O Id é diferente de NULL na inserção...");
		}
		businessLayer.insert(game);
		return ResponseEntity.ok(game);
	}
	
	@PatchMapping("games/{id}/vote")
	public ResponseEntity<Void> votee(@PathVariable Long id) {
		this.businessLayer.vote(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("games/{id}")
	public ResponseEntity<Game> insert(@PathVariable Long id, @RequestBody Game game) {
		businessLayer.update(id, game);
		return ResponseEntity.ok(game);
	}

	@DeleteMapping("games/{id}")
	public ResponseEntity<Game> delete(@PathVariable Long id) {
		businessLayer.delete(id);
		return ResponseEntity.ok().build();
	}

}
