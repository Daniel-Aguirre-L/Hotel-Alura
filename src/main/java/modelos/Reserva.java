package modelos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {
	

	public Reserva(Long id, LocalDate fechaEntrada, LocalDate fechaSalida, Double valor, String formaPago,
			Set<Huesped> huespedes) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
		this.huespedes = huespedes;
	}
	
	

	public Reserva() {
		super();
	}



	public Set<Huesped> getHuespedes() {
		return huespedes;
	}

	public void setHuespedes(Set<Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "forma_pago")
    private String formaPago;

  @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
  private Set<Huesped> huespedes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
    
  
}


