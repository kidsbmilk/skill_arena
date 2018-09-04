package acm.HDOJ_Java.Hard;

/***************************************************************************
 *								                                            *
 *	秒钟的速度s=6°/s，分针是1/10°/s，时针是1/120°/s			                    *
 *	所以相对速度s_m=59/10°/s，s_h=719/120°/s，m_h=120/11°/s		            *
 *	所以相差一度所需要的时间sm=10/59 s/°，sh=120/719 s/°，mh=120/11 s/°         *
 *	他们差360°的周期为tsm=3600/59 s，tsh=43200/719 s，tmh=43200/11 s	        *
 *	需要相差的角度为n。                                                        *
 *	rsm>n → n*sm+k1*tsm < t < tsm-n*sm+k1*tsm;			                    *
 *	rsh>n → n*sh+k2*tsh < t < tsh-n*sh+k2*tsh;		                        *
 *	rmh>n → n*mh+k3*tmh < t < tmh-n*mh+k3*tmh;			                    *
 *	三个条件都满足所占的总时间即为时针、分针、秒针相差角度大于n的总时间              *
 *								                                            *
 ***************************************************************************/

import java.util.Scanner;

public class Tick_and_Tick {
//    public class Main {
    public static void main(String[] args) {
        int t;
        double n, sum, ft1, ft2, ft3, et1, et2, et3, max, min;
        double sm, sh, mh, tsm, tsh, tmh, fsm, fsh, fmh, esm, esh, emh;
        sm = 10. / 59.;
        sh = 120. / 719.;
        mh = 120. / 11.;
        tsm = 360 * sm;
        tsh = 360 * sh;
        tmh = 360 * mh;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            n = sc.nextInt();
            if(n < 0) {
                break;
            }

            sum = 0;
            fsm = sm * n;
            fsh = sh * n;
            fmh = mh * n;
            esm = tsm - fsm;
            esh = tsh - fsh;
            emh = tmh - fmh;
            for ( ft3 = fmh, et3 = emh; et3 <= 43200; et3 += tmh, ft3 += tmh) {
                for ( ft2 = fsh, et2 = esh; et2 <= 43200; et2 += tsh, ft2 += tsh) {
                    if(et2 < ft3) {
                        continue;
                    }
                    if(ft2 > et3) {
                        break;
                    }
                    for ( t = 0, ft1 = fsm, et1 = esm; et1 <= 43200; t = t + 1, et1 = esm + t * tsm, ft1 = fsm + t * tsm) {
//                    for ( ft1 = fsm, et1 = esm; et1 <= 43200; et1 += tsm, ft1 += tsm) { // 这个过不了，可能是加法存在误差。
                        if( et1 < ft3 || et1 < ft2) {
                            continue;
                        }
                        if(ft1 > et3 || ft1 > et2) {
                            break;
                        }
                        max = ft1;
                        if(ft2 > max) {
                            max = ft2;
                        }
                        if(ft3 > max) {
                            max = ft3;
                        }
                        min = et1;
                        if(et2 < min) {
                            min = et2;
                        }
                        if(et3 < min) {
                            min = et3;
                        }
                        sum += min - max;
                    }
                }
            }
            sum /= 432.;
            System.out.printf("%.3f", sum); // "0.3f\n"时过不了，不知道为什么 ?
            System.out.println();
        }
    }
}
