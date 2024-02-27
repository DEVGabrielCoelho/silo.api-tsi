package br.com.telematica.siloapi.model.enttity;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "planta")
public class PlantaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private BigInteger placod;
	@Column(length = 100, nullable = false)
	private String planom;
	@Column(nullable = false)
	private BigInteger empcod;

}
