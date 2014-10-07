<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="inc/style.css" />" />
        <script src="<c:url value="inc/jquery-1.11.1.min.js" />"></script>
    </head>
    <body>
       	<c:import url="inc/menu.jsp" />
        <div>
            <form method="post" action="<c:url value="creationCommande" />">
                <fieldset>
                    <legend>Voulez-vous créer un nouveau client ?</legend>
	            	<div class="radio">
					  <label>
					    <input type="radio" name="optionsRadios" id="optionsRadios1" value="nouveau" onClick="displayAndHideForms('#optionBloc_nouveauClient','#optionBloc_existant');" <c:if test="${(!empty form.clientType) && (form.clientType == 'nouveau') }">checked</c:if>>
					    Oui
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" name="optionsRadios" id="optionsRadios2" value="existant"  onClick="displayAndHideForms('#optionBloc_existant','#optionBloc_nouveauClient');" <c:if test="${(!empty form.clientType) && (form.clientType == 'existant') }">checked</c:if>>
					    Non
					  </label>
					</div>
                </fieldset>
            
                <c:set var="client" value="${ commande.client }" scope="request" />
                <div id="optionBloc_nouveauClient" <c:if test="${(empty form.clientType) || (form.clientType != 'nouveau') }">style="display:none;"</c:if>>
            		<c:import url="inc/inc_clientForm.jsp" />
            	</div>
                <div id="optionBloc_existant"  <c:if test="${(empty form.clientType) || (form.clientType != 'existant') }">style="display:none;"</c:if>>
	                <fieldset>
	                    <legend>Liste des clients existant:</legend>
	            		<select id="clientExistant" name="clientExistant">
	            			<c:if test="${!empty sessionScope.listeClient }">
								<c:forEach items="${sessionScope.listeClient }" varStatus="status" var="liste">
	            					<option value='<c:out value="${liste.value.nom  }" />'  <c:if test="${(!empty client.nom) && (client.nom == liste.value.nom) }">selected</c:if>><c:out value="${liste.value.nom } - ${liste.value.prenom }" /></option>
	            				</c:forEach>
	            			</c:if>
	            		</select>
	            		<span class="erreur"><c:out value="${form.erreurs['clientExistant']}" /></span>
                    	<br />
               		</fieldset>
            	</div>
                <fieldset>
                    <legend>Informations commande</legend>
                     
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="<c:out value="${commande.dateCommande }" />" size="20" maxlength="20" disabled />
                    <br />
                     
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="<c:out value="${commande.montant }" />" size="20" maxlength="20" />
    				<span class="erreur"><c:out value="${form.erreurs['montantCommande']}" /></span>
                    <br />
                     
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${commande.modeDePaiement }" />" size="20" maxlength="20" />
    				<span class="erreur"><c:out value="${form.erreurs['modePaiementCommande']}" /></span>
                    <br />
                     
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${commande.statutDePaiement }" />" size="20" maxlength="20" />
    				<span class="erreur"><c:out value="${form.erreurs['statutPaiementCommande']}" /></span>
                    <br />
                     
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${commande.modeDeLivraison }" />" size="20" maxlength="20" />
    				<span class="erreur"><c:out value="${form.erreurs['modeLivraisonCommande']}" /></span>
                    <br />
                     
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${commande.statutDeLivraison }" />" size="20" maxlength="20" />
    				<span class="erreur"><c:out value="${form.erreurs['statutLivraisonCommande']}" /></span>
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
                <p class="info">${ form.message }</p>
            </form>
        </div>
    </body>
</html>
<script>
	function displayAndHideForms( idToDisplay , idToHide){
		$(idToDisplay).show();
		$(idToHide).hide();
	}
</script>