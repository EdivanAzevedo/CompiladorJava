package tokens;

public class AlgumaLexico {
	private LeitorArquivoTexto texto;
	
	public AlgumaLexico(String arquivo)
	{
		this.texto = new LeitorArquivoTexto(arquivo);
	}
	
	public Token proximoToken()
	{
		Token proximo = null;
		
		this.espacosComentario();
		this.texto.confirmar();
		
		proximo = this.fim();
		if(proximo == null)
		{
			this.texto.limpar();
		}else
		{
			this.texto.confirmar();
			return proximo;
		}
		
		proximo = this.palavraChave();
		if(proximo == null)
		{
			this.texto.limpar();
		}else
		{
			this.texto.confirmar();
			return proximo;
		}
		
		proximo = this.variavel();
		if(proximo == null)
		{
			this.texto.limpar();
		}else
		{
			this.texto.confirmar();
			return proximo;
		}
		
		proximo = this.delimitador();
		if(proximo == null)
		{
			this.texto.limpar();
		}else
		{
			this.texto.confirmar();
			return proximo;
		}
		
		return proximo;
		
		
//		int caractereLido = -1; // this.texto.lerProximoCaractere();
//		/*
//		 * if(caractereLido == -1) return null;
//		 */
//		do
//		{
//			char c = (char) caractereLido;
//			if(c == ' ' || c == '\n')
//				continue;
//			if(c == ':')
//				return new Token(TipoToken.PCDelim,":");
//			else if(c == '*')
//				return new Token(TipoToken.OpAritiMult, "*");
//			else if(c == '+')
//				return new Token(TipoToken.OpAritSoma, "+");
//			else if(c == '-')
//				return new Token(TipoToken.OpAritSub, "-");
//			else if(c == '/')
//				return new Token(TipoToken.OpAritDiv, "/");
//			else if(c == '>')
//			{
//				caractereLido = (char) texto.lerProximoCaractere();
//				
//				c = (char) caractereLido;
//				
//				if(c == '=')
//					return new Token(TipoToken.OpRelMaiorIgual, ">=");
//				else
//					return new Token(TipoToken.OpRelMaior, ">");
//			}else if(c == '<')
//			{
//				caractereLido = (char) texto.lerProximoCaractere();
//				
//				c = (char) caractereLido;
//				
//				if(c == '>')
//					return new Token(TipoToken.OpRelDif, "<>");
//				else if(c == '=')
//					return new Token(TipoToken.OpRelMenorIgual, "<=");
//				else
//					return new Token(TipoToken.OPRelMenor, "<");
//			}
//			
//		}while((caractereLido = texto.lerProximoCaractere()) != -1);
	}
	
	private Token delimitador() {
		int caractereLido = this.texto.lerProximoCaractere();
		if((char) caractereLido == ':')
		{
			return new Token(TipoToken.PCDelim, ":");
		}
		return null;
	}

	private Token fim() {
		int caractereLido = this.texto.lerProximoCaractere();
		if(caractereLido == -1)
		{
			return new Token(TipoToken.Fim, "Fim");
		}
		return null;
	}

	private Token espacosComentario() {
		return null;
	}

	private Token operadorRelacional(){
		return null;
	}
	
	private Token palavraChave(){
		while(true)
		{
			char c = (char) this.texto.lerProximoCaractere();
			
			if(!Character.isLetter(c))
			{
				this.texto.retroceder();
				String lexema = texto.getLexema();
				
				if(lexema.equals("DECLARACOES"))
				{
					return new Token(TipoToken.PCDeclaracoes, lexema);
				} else if (lexema.equals("INT"))
				{
					return new Token(TipoToken.PCInteiro, lexema);
				}else 
				{
					return null;
				}
			}
		}
	}
	
	private Token parenteses()
	{
		return null;
	}
	
	private Token numeros()
	{
		return null;
	}
	
	private Token variavel()
	{
		int estado = 1;
		
		while(true)
		{
			char c = (char) this.texto.lerProximoCaractere();
			
			if(estado == 1)
			{
				if(Character.isLetter(c))
				{
					estado = 2;
				}else
				{
					return null;
				}
			}else if(estado == 2)
			{
				if(!Character.isLetterOrDigit(c))
				{
					this.texto.retroceder();
					return new Token(TipoToken.Var, this.texto.getLexema());
				}
			}
		}
	}
}
