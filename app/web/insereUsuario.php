<?php 

include 'connect.php';


//se tipo == 1 então insere, se tipo==2 então atualiza os dados

if (isset($_POST['tipo']))
{
	$tipo = $_POST['tipo'];

}

if (isset($_POST['idUsuario']))
{
	$idUsuario = $_POST['idUsuario'];
}

if (isset($_POST['usuarios']))
{
	$usuarios = $_POST['usuarios'];
	$d=json_decode($usuarios,true);
	
	if ($tipo==1)
	{
		$sql = "insert into Usuario (Nome,Endereco,Telefone,Email,NomeUsuario,Senha,DataUltimoAcesso,
			HoraUltimoAcesso,CadastroValido) values (\"$d[Nome]\",\"$d[Endereco]\",\"$d[Telefone]\",\"$d[Email]\",
			\"$d[NomeUsuario]\",\"$d[Senha]\",\"$d[DataUltimoAcesso]\",\"$d[HoraUltimoAcesso]\",$d[CadastroValido])";
	}
	else if ($tipo==2)
	{
		
		$sql = "update Usuario SET idUsuarios = $d[idUsuario],Nome=\"$d[Nome]\",
		Endereco=\"$d[Endereco]\",Telefone=\"$d[Telefone]\",Email=\"$d[Email]\",NomeUsuario=\"$d[NomeUsuario]\",
		Senha=\"$d[Senha]\",DataUltimoAcesso=\"$d[DataUltimoAcesso]\",HoraUltimoAcesso=$d[HoraUltimoAcesso],
		CadastroValido=$d[CadastroValido] where idUsuarios=$d[idUsuario]";
	}

}


$res = mysqli_query($conn,$sql) or die ("Erro na consulta" .mysqli_error($conn));


if ($res)	
{
	//retorna o ID do campo inserido
	$result["result"]=mysqli_insert_id($conn);
				
}
else
{
	$result["result"]=0;
}

echo json_encode($result);

mysqli_close($conn);

?>