package com.iadvize.conversation.sdk.demo.feature.product

import android.os.Parcelable
import com.iadvize.conversation.sdk.demo.R
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Product(
    val pictureResId: Int,
    val name: String,
    val description: String,
    val oldPrice: String = randomPrice(false),
    val newPrice: String = randomPrice(true)
) : Parcelable

val products: List<Product> = listOf(
    Product(
        R.drawable.product_belkin,
        "Belkin Charging Stand",
        "Logoden biniou degemer mat an penn ar bed hent loen a seul c’houzoug Groe, baradoz dirak Bro Baz trec’h fiñval panevet  roched c’hroaz An Alre adarre c'hlas eost daouarn, montr antronoz Naoned hanter bodañ nemet  evit bourc’h kreiz c’henwerzh kuzuliañ Nedeleg."
    ),
    Product(
        R.drawable.product_tile,
        "Tile Pro Tracker",
        "Skrivañ krib c’hoar c’henwerzh waz evito ur rev distreiñ Pont-Aven koar ha darn beajiñ, puñs koumanant pennad egistout santout banne degas speredek brav Mellag maouez kloued Pont’n-Abad er, gentañ naontek aon warnon disadorn ha ennon e kibell nemet yod vuoc’h."
    ),
    Product(
        R.drawable.product_ecobee,
        "ecobee4 Thermostat",
        "Grizilh hepken warc’hoazh gwerzhañ heuliañ kreion Pempoull strad An Alre kelenn vouezh dleout izel kotoñs, penn ar krediñ war Sant-Gwenole pignat muiañ wechoù blev Briad fentigelloù ken warni, jod, dija reiñ tal diwezhañ avel ar lec’h dezhañ tenn gwaz troad kerzhout."
    ),
    Product(
        R.drawable.product_technica,
        "Technica AT-LP3WH",
        "Ur jiletenn warnomp marc’h votez kribañ baradoz war sadorn verc’h a piz gouzañv Plegad-Gwerann familh hennezh Plelann-Vihan priz falc’hat goleiñ an, mintin kaozeadenn trec’h biken arabat war aet pakad bed roc’h formaj a nerzh sailh penn Montroulez perc’henn huñvre Kermouster."
    ),
    Product(
        R.drawable.product_oculus,
        "Oculus Go Standalone",
        "Dremm pediñ lezirek Gwaien soudard Plouneour-Menez warni, marc’h gwech antronoz walc’h, meurzh pevarzek echu c’hof bodañ ar glas huñvre plij niz, ledan Arre gwinizh c’hwezek karantez Plouzane bugel soñjal doujañs dro, hiziv dour eürus ha digarez mae tevel ha pegoulz."
    ),
    Product(
        R.drawable.product_fitbit,
        "Fitbit Versa Smartwatch",
        "Falc’hat nadoz e pleg amanenn bragoù kelien diwezhañ neuiñ Sun c’hreisteiz Lokmikael (an-Traezh) Lanuon ganti, vezañ Roazhon Park gwechall bennak e bazh bleun mezheven lenn nemet  per hini dimeziñ nebeutoc’h, naer kalet da tog voutailh yalc’h melen sell kouezhañ bluenn c’hazh eñ."
    ),
    Product(
        R.drawable.product_frame,
        "Samsung The Frame",
        "Dud betek gazek froud voull bouzar eizh ha speredek hanternoz roll yalc’h tach amezeg, vi nemet  Penmarc’h Douarnenez vaneg eme  egiston karrezek ouzhin poultrenn honnezh eured tresañ pal, disheol chal gounez mervel ar aer ur ha bleud e bremañ montr."
    ),
    Product(
        R.drawable.product_pod,
        "Apple HomePod",
        "Davañjer maouez disadorn pepr Gwened etre ruz lestr frouezh gazek tal troc’hañ sivi anal, kuzh sae stivell paper onest roll traonienn gwec’h laerezh rumm skuizhañ koll e Montroulez, deoc'h c’helien kribañ degemer kement diouzhtu burzhud Plourin-Montroulez kelc’h dija kennebeut diwar."
    ),
    Product(
        R.drawable.product_earbuds,
        "Bose sleepbuds™",
        "Honnezh hi benn voulouz Kembre daouarn glin gwriat gwern mel da skeudenn disadorn a, c’har plant dro yalc’h dreuz walc’h evezh an here sukr nec’h kaol egistañ ti, bouzar e esaeañ kregiñ soavon Atlantel Douarnenez dimeurzh deñvalijenn ganto kichen levrioù."
    ),
    Product(
        R.drawable.product_qled,
        "Samsung Q9FN QLED",
        "Gallek arc’hant dremm hep dimerc’her meurzh enebour Pornizhan kein vrec’h endervezh skrijañ fresk anezhañ, ur druez da eme da nec’hin merenn e peoc’h nebeut an walc’h paotrig kalz, kaozeadenn dreuz tavarn doujañs ennon eil gwener C’hall greiz lestr mantell talvoudus."
    )
)

fun randomPrice(promo: Boolean): String {
    val euros = Random.nextInt(200) + if (promo) 800 else 1000
    val cents = Random.nextInt(89) + 10
    return "$euros,$cents€"
}
