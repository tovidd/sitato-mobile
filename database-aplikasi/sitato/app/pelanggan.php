<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class pelanggan extends Model
{
    protected $table= 'pelanggan';
    protected $primaryKey= 'id_pelanggan';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'nama_pelanggan',
        'no_telepon_pelanggan',
        'alamat_pelanggan'
    ];
}
