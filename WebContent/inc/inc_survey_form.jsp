<%@ page pageEncoding="UTF-8" %>

<label for="subjectField">Sujet <span class="requis">*</span></label>
						
<input type="text" id="subjectField" name="subjectField" value="" size="50" maxlength="50" />

<br />
<span class="erreur">${form.errors['subjectField']}</span>
<br />

<input type="radio" name="statusField" value="active" checked />  Actif
<input type="radio" name="statusField" value="inactive" /> Inactif<br>


<br />
<span class="erreur">${form.errors['statusField']}</span>
<br />
