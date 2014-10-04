<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
    </head>
    <body>
       	<c:import url="inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="creationCommande" />">
            	<c:import url="inc/inc_clientForm.jsp" />
                <fieldset>
                    <legend>Informations commande</legend>
                     
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="<c:out value="${commande.dateCommande }" />" size="20" maxlength="20" disabled />
                    <br />
                     
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="<c:out value="${commande.montant }" />" size="20" maxlength="20" />
    				<span class="erreur">${commandeForm.erreurs['montantCommande']}</span>
                    <br />
                     
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${commande.modeDePaiement }" />" size="20" maxlength="20" />
    				<span class="erreur">${commandeForm.erreurs['modePaiementCommande']}</span>
                    <br />
                     
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${commande.statutDePaiement }" />" size="20" maxlength="20" />
    				<span class="erreur">${commandeForm.erreurs['statutPaiementCommande']}</span>
                    <br />
                     
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${commande.modeDeLivraison }" />" size="20" maxlength="20" />
    				<span class="erreur">${commandeForm.erreurs['modeLivraisonCommande']}</span>
                    <br />
                     
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${commande.statutDeLivraison }" />" size="20" maxlength="20" />
    				<span class="erreur">${commandeForm.erreurs['statutLivraisonCommande']}</span>
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
                <p class="${empty commandeForm.erreurs ? 'succes' : 'erreur'}">${commandeForm.message}</p>
            </form>
        </div>
    </body>
</html>