package com.example.task_manager.classes;

import java.time.LocalDate;
import java.util.Objects;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
    //Para o id ser criado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private GrauUrgencia grauUrgencia;
    
    private LocalDate dataCriada;

    public Task(){ }

    public Task(Integer id){
        this.id = id;
        this.dataCriada = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public GrauUrgencia getGrauUrgencia() {
        return grauUrgencia;
    }

    public void setGrauUrgencia(GrauUrgencia grauUrgencia) {
        this.grauUrgencia = grauUrgencia;
    }

    public void setDataCriada(LocalDate now) {
        this.dataCriada = now;
    }

    public LocalDate getDataCriada(){
        return this.dataCriada;
    }

    
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;

    //     Task task = (Task) o;

    //     return id != null && id.equals(task.id);
    // }

    // @Override
    // public int hashCode() {
    //     return id != null ? id.hashCode() : 0;
    // }

     @Override
    public boolean equals(Object obj) {
        if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        
        Task other = (Task) obj;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
