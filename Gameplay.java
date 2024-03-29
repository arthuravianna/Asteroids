package asteroids;

import java.awt.Color;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Physics;
import jplay.Window;

public class Gameplay {
	Window janela;
	GameImage fundo;
	Keyboard teclado;
	Physics fisica;
	Nave player;
	int Score[] = {0};
	int qtd_asteroides = 4;
	int asteroides_destruidos = 0;
	Asteroides asteroides;
	Ovni alien = new Ovni();
	boolean executando = true;
	
	public Gameplay(Window janela) {
		carregar(janela);
		loop();
	}
	
	public void carregar(Window janela) {
		this.janela = janela;
		this.teclado = janela.getKeyboard();
		this.fisica = new Physics();
		fisica.setGravity(0);
		fisica.createWorld(this.janela.getWidth(), this.janela.getHeight());
		this.fundo = new GameImage("Images/background.png");
		this.player = new Nave();
		fisica.createBodyFromSprite(player, false);
		player.setMass(1f);
		player.setFriction(0.2f);
		player.setRestitution(0f);
		player.setX(janela.getWidth()/2 - player.width/2);
		player.setY(janela.getHeight()/2 - player.height/2);
		fisica.createBodyFromSprite(player, false);
		asteroides = new Asteroides(qtd_asteroides);
	}
	
	public void desenha() {
		this.fundo.draw();
		alien.movimenta(janela); //movimenta e desenha o Ovni
		asteroides.desenha(janela); //movimenta e desenha os asteroids
		player.desenha_vidas();
		janela.drawText("Score: "+Score[0], 0, 10, new Color(255,255,255));
		player.verif_tela(janela);
		this.player.draw();
		player.tiro.desenha_tiro();
		player.tiro.upadate_tiro(janela, asteroides, Score, alien, player);
		fisica.update();
		this.janela.update();
		janela.delay(12);
	}
	
	public void loop() {
		while(executando) {
			desenha();
			alien.sorteia(player.get_vidas().size()); //Verifica se um Ovni vai aparecer
			if (player.cont_ressurection > player.cooldown_ressurection) {
				player.loadImage("Images/nave11.png");
				if (asteroides.colisao_jogador(player, janela)) {
					new Gameover(Score[0], janela);
					return;
				}
			}
			else {
				player.loadImage("Images/nave11Ghost.png");
			}
			if (asteroides.pedrinhas.size() == 0) {//recomeça se todos foram destruídos
				qtd_asteroides += 1;
				asteroides = new Asteroides(qtd_asteroides);
			}
			player.ultimo_disparo += janela.deltaTime(); //delta time retorna o tempo em milisegundos
			player.cont_ressurection += janela.deltaTime();//conta o tempo desde que o player morreu
			player.getBody().setAngularVelocity(0);
			//comandos do jogador
			if (teclado.keyDown(Keyboard.LEFT_KEY)) {
				player.getBody().setAngularVelocity(3);
			}
			else if (teclado.keyDown(Keyboard.RIGHT_KEY)) {
				player.getBody().setAngularVelocity(-3);
			}
			if (teclado.keyDown(Keyboard.UP_KEY)){
				player.acelera();
			}
			if (teclado.keyDown(Keyboard.SPACE_KEY) && player.ultimo_disparo > player.reload) {
				player.tiro.inicializa_tiro(player, fisica);
				player.ultimo_disparo = 0;
			}
			else if (teclado.keyDown(Keyboard.ESCAPE_KEY)) {
				return;
			}
		}
	}
}
