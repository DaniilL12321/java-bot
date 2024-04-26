package ru.lobanov.projects.javabot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "clients_roles")
@Entity(name = "clients_roles")
@AllArgsConstructor
@NoArgsConstructor
public class ClientsRole {
    @Id
    @GeneratedValue(generator = "clients_role_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "clients_role_id_seq", sequenceName = "clients_role_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated
    private ClientsAuthority clientsAuthority;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clients_id")
    private Clients clients;

}