package com.formation.apirestdemo.service;

import com.formation.apirestdemo.model.Produit;
import com.formation.apirestdemo.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock                    // faux repository, piloté par le test
    private ProduitRepository produitRepository;

    @Mock                    // fausse notification : aucun message réellement envoyé
    private NotificationService notificationService;

    @InjectMocks             // vrai service, construit avec les deux mocks
    private ProduitService produitService;

    @Test
    void recupererParId_retourneLeProduit_quandIlExiste() {
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Clavier");
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

        Optional<Produit> resultat = produitService.recupererParId(1L);

        assertThat(resultat).isPresent();
        assertThat(resultat.get().getNom()).isEqualTo("Clavier");
    }

    @Test
    void supprimer_retourneFalse_etNeSupprimeRien_siLeProduitNExistePas() {
        when(produitRepository.existsById(99L)).thenReturn(false);

        boolean resultat = produitService.supprimer(99L);

        assertThat(resultat).isFalse();
        verify(produitRepository, never()).deleteById(any());
    }

    @Test
    void creer_forceLIdANull_avantLaSauvegarde() {
        Produit produit = new Produit("Clavier", "Mécanique", 89.99, 10);
        produit.setId(42L); // un client malin tente d'imposer un ID

        produitService.creer(produit);

        // Le captor récupère l'objet réellement passé à save()
        ArgumentCaptor<Produit> captor = ArgumentCaptor.forClass(Produit.class);
        verify(produitRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
    }

    @Test
    void creer_appelleNotifier_uneSeuleFois() {
        Produit produit = new Produit("Clavier", "Mécanique", 89.99, 10);

        produitService.creer(produit);

        verify(notificationService, times(1)).notifier("stock", "Nouveau produit : Clavier");
    }
}
