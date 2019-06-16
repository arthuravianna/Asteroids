package asteroids;

import java.util.ArrayList;
import java.util.List;

import jplay.GameImage;
import jplay.Sprite;

public class Nave extends Sprite{
	private List<GameImage> vidas = new ArrayList<>();
	//o jogador tem 3 segundos para ir para uma posicao segura após morrer
	public int cooldown_ressurection = 3000;
	public int cont_ressurection = 3000;
	public Tiro tiro = new Tiro();
	public int ultimo_disparo = 0;
	public int reload = 500; //tempo em milisegundos
	
	public Nave() {
		super("Images/nave11.png");
		//o jogador começa com três vidas
		for (int i = 0; i < 3; i++) {
			adiciona_vida();
		}
	}
	
	public void acelera() {
		if (cont_ressurection > cooldown_ressurection) {
			this.loadImage("Images/nave21.png");
		}
		else {
			this.loadImage("Images/nave21Ghost.png");
		}
		this.applyForceY(15*Math.sin(this.getBody().getAngle()));
		this.applyForceX(15*Math.cos(this.getBody().getAngle()));
	}
	
	public void adiciona_vida() {
		GameImage nova_vida = new GameImage("Images/vida.png");
		nova_vida.x = nova_vida.width * (vidas.size());
		nova_vida.y = nova_vida.height;
		vidas.add(nova_vida);
	}
	public void perde_vida() {
		vidas.remove(vidas.size() -1);
	}
	public void desenha_vidas() {
		for (GameImage vida: vidas) {
			vida.draw();
		}
	}
	public List<GameImage> get_vidas(){
		return vidas;
	}
}
