<?php 

include 'connect.php';


//pega os dados de todos os tenistas de um determinado ranking... inclusindo nome usuário, endereço, telefone, etc...

if (isset($_GET['idRanking']))
{
	$idRanking=$_GET['idRanking'];
	$sql = "select usuario.idUsuarios,usuario.Nome,usuario.Endereco,usuario.Telefone,usuario.Email,usuario.NomeUsuario,usuario.CadastroValido,tenista.idTenistas,tenista_has_rankinghistorico.Posicao,tenista.Categoria_idCategoria  from usuario,tenista,tenista_has_rankinghistorico,rankingHistorico where rankingHistorico.idRankingHistorico='$idRanking' AND rankinghistorico.idRankingHistorico=tenista_has_rankinghistorico.RankingHistorico_idRankingHistorico AND tenista_has_rankinghistorico.Tenista_idTenistas=tenista.idTenistas AND usuario.idUsuarios=tenista.Usuarios_idUsuarios order by tenista_has_rankinghistorico.Posicao;
			";
}



	

if (isset($_GET['idRanking']))
{	
$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if (mysqli_num_rows($res) > 0)
{

	$result["tenistas"]=array();



	while ($row = mysqli_fetch_array($res))
	{
		$tenistas = array();
		$tenistas["idTenistas"] = $row["idTenistas"];
		$tenistas["idUsuarios"] = $row["idUsuarios"];
		$tenistas["PosicaoAtualRanking"] = $row["Posicao"];
		$tenistas["idCategoria"]=$row["Categoria_idCategoria"];
		$tenistas["Nome"] = $row["Nome"];
		$tenistas["Endereco"] = $row["Endereco"];
		$tenistas["Telefone"] = $row["Telefone"];
		$tenistas["Email"] = $row["Email"];
		$tenistas["NomeUsuario"]=$row["NomeUsuario"];
		$tenistas["CadastroValido"]=$row["CadastroValido"];
		
		array_push($result["tenistas"],$tenistas);
		//array_push($result,array('id'=>$row[0],'nome'=>$row[1],'endereco'=>$row[2],'telefone'=>$row[3],'email'=>$row[4],'usuario'=>$row[5],'senha'=>$row[6]));
	}
	
		$result["result"]=1;
				
}
else
{
	$result["result"]=0;
}

}
else 
	$result["result"]=0;

echo json_encode($result);

		mysqli_close($conn);

?>