<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class transaksiPenjualanJasaService extends Model
{
    protected $table= 'transaksi_penjualan_jasa_service';
    protected $primaryKey= 'id_transaksi_penjualan_jasa_service';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'id_transaksi',
        'id_montir',
        'id_jasa_service',
        'id_kendaraan',
        'jumlah_transaksi_penjualan_jasa_service',
        'subtotal_transaksi_penjualan_jasa_service'
    ];
}
