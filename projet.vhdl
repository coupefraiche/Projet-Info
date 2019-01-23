----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    10:32:48 01/16/2019 
-- Design Name: 
-- Module Name:    entre - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity entre is
    Port ( EDETEC : in  STD_LOGIC;
           CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
           SDETEC : out  STD_LOGIC_VECTOR (6 downto 0);
           SBITPAR : out  STD_LOGIC;
           SVAL : out  STD_LOGIC);
end entre;



architecture Behavioral of entre is
type etat is (un,deux,trois,quatre,cinq,six,sept);
signal etat_present : etat;
signal etat_futur : etat;
signal EN_TETE :STD_LOGIC;
signal QQ : STD_LOGIC_VECTOR (6 downto 0);
begin

--DEFINIR LES ETATS
process (CLK,RST,etat_futur)
begin
if RST='1' then etat_present<=un;	
						
	elsif (CLK'event and CLK='1') then etat_present<=etat_futur;
end if;	
end process;

--DETECTION DE L'ENTETE "101011"
process(EDETEC,etat_present)
begin
etat_futur<=etat_present;
EN_TETE<='0';
case etat_present is

	when un 		=> if EDETEC='1' then etat_futur<=deux;
								end if;
					
	when deux	=> if EDETEC='0' then etat_futur<=trois;
						end if;
						
	when trois =>	if EDETEC='1' then etat_futur<=quatre;
									 else etat_futur<=un;
						end if;
						
	when quatre => if EDETEC='0' then etat_futur<=cinq;
									 else etat_futur<=deux;		 
						end if;
						
	when cinq => if EDETEC='1' then etat_futur<=six;
									 else etat_futur<=un;		 
						end if;
						
	when six => if EDETEC='1' then etat_futur<=sept;
									 else etat_futur<=cinq;		 
						end if;
						
	when sept => if EDETEC='1' then etat_futur<=deux;
									 else etat_futur<=un;		 
						end if;
						EN_TETE<='1';
						
	when others => null;					

	end case;
end process;



--TRANSFORMATION SERIE-PARRALLELE
process(CLK)
variable compteur : integer;
variable verif_en_tete : integer; 
begin
if EN_TETE = '1' then verif_en_tete:= 1;end if;
if CLK='1' and CLK'event then 
		if verif_en_tete = 1 then
					QQ <= "0000000";
						--if compteur = 6 then compteur := 0; end if;

		end if;
end if ;
end process;

SDETEC <= QQ ;

end Behavioral;

