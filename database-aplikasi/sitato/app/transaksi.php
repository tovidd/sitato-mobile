<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class transaksi extends Model
{
    protected $table= 'transaksi';
    protected $primaryKey= 'id_transaksi';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'id_pelanggan',
        'id_cs',
        'id_kasir',
        'id_status_pengerjaan',
        'total_transaksi',
        'diskon_transaksi',
        'jumlah_uang_pembayaran_transaksi'
    ];
}
