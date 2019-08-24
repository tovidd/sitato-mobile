<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class transaksiPenjualanSparepart extends Model
{
    protected $table= 'transaksi_penjualan_sparepart';
    protected $primaryKey= 'id_transaksi_penjualan_sparepart';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'id_transaksi',
        'id_sparepart',
        'jumlah_transaksi_penjualan_sparepart',
        'subtotal_transaksi_penjualan_sparepart'
    ];
}
